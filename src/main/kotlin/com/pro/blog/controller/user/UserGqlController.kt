package com.pro.blog.controller.user

import cn.dev33.satoken.reactor.context.SaReactorHolder
import cn.dev33.satoken.stp.StpUtil
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import com.pro.blog.annotation.LoginCheck
import com.pro.blog.dto.LoginInfo
import com.pro.blog.exception.customer.BadRequestException
import com.pro.blog.pojo.mongo.User
import com.pro.blog.service.UserService
import kotlinx.coroutines.reactor.awaitSingle

import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.Duration

@DgsComponent
class UserGqlController {

    @Autowired
    private lateinit var userService: UserService

    @DgsQuery
    fun registered(@InputArgument name: String): Mono<Boolean> {
        return userService
            .findUserByName(name)
            .log()
            .any {
                true
            }
            .defaultIfEmpty(false)
    }

    @DgsQuery
    fun pbUserInfo(@InputArgument id: String): Mono<User> {
        return userService
            .userInfoById(id)
    }

    @LoginCheck
    @DgsQuery
    fun pvUserInfo(): Mono<User> {
        return userService
            .userInfoById(StpUtil.getLoginId("")!!)
    }

    @DgsMutation
    fun passwordLogin(@InputArgument user: User): Mono<LoginInfo> {
        return userService
            .userLoginByNameAndPassword(user.userName!!, user.password!!)
            .log()
            .flatMap { u ->
                SaReactorHolder.getContextAndSetSync().map {
                    StpUtil.login(u.id)
                    LoginInfo(
                        u,
                        StpUtil.getTokenInfo()
                    )
                }
            }
//        val userInfo = userService
//            .userLoginByNameAndPassword(user.userName!!, user.password!!)
//            .share()
//            .block()
//
//        StpUtil.login(userInfo?.id)
//
//        return Mono.just(
//            LoginInfo(
//                userInfo,
//                StpUtil.getTokenInfo()
//            )
//        )

    }

    @DgsMutation
    fun passwordRegister(@InputArgument user: User): Mono<User> {
        return userService
            .canRegister(user)
            .log()
            .filter {
                it
            }.flatMap {
                userService.userRegister(user)
            }
            .switchIfEmpty {
                throw BadRequestException("register failure")
            }
    }
}