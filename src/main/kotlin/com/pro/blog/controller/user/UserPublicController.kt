package com.pro.blog.controller.user

import cn.dev33.satoken.reactor.context.SaReactorHolder
import cn.dev33.satoken.stp.StpUtil
import cn.dev33.satoken.util.SaResult
import com.pro.blog.dto.LoginInfo
import com.pro.blog.dto.UserInfo
import com.pro.blog.dto.toUserInfo
import com.pro.blog.pojo.mongo.User
import com.pro.blog.service.UserService
import com.pro.blog.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api/user/pub")
class UserPublicController constructor(
    @Autowired private val userService: UserService,
) {
    @PostMapping(value = ["/login/pwd"])
    fun userLoginWithPassword(@RequestBody user: User): MR {
        val userInfo = user.userName?.let { name -> user.password?.let { pwd -> userService.userLoginByNameAndPassword(name, pwd) } }
        return userInfo
            ?.log()
            ?.flatMap { u ->
                SaReactorHolder.getContextAndSetSync().map {
                    StpUtil.login(u.id)
                    result200(object {
                        val userInfo = UserInfo.toUserInfo(u)
                        val tokenInfo = StpUtil.getTokenInfo()
                    })
                }
            }
            ?.defaultIfEmpty(result400("username or password wrong"))
            ?: monoResult(result400("Please provide login parameter"))
    }

    @PostMapping("/register/pwd")
    fun userRegisterWithPassword(@RequestBody user: User?): MR {

        val canRegister = user?.let {
            userService.canRegister(it)
        }
        return canRegister
            ?.flatMap {
                userService.userRegister(user)
            }
            ?.mapNotNull {
                result200(UserInfo.toUserInfo(it))
            }
            ?.defaultIfEmpty(result500("register error"))
            ?: monoResult(result400("user data empty"))
    }

    @GetMapping("/name_register/{name}")
    fun checkWhetherNameRegister(@PathVariable("name") name: String): MR {
        return userService
            .findUserByName(name)
            .log()
            .any {
                true
            }
            .map {
                result200("register successfully")
            }
            .defaultIfEmpty(result400("name registered"))
    }

    @GetMapping("/search/{uid}")
    fun publicUserInfo(@PathVariable("uid") uid: String): MR {
        return userService
            .userInfoById(uid)
            .map {
                result200(UserInfo.toUserInfo(it))
            }
            .defaultIfEmpty(result400("User Not Existing"))
    }

    @GetMapping("/test2")
    fun test2(): R {
        StpUtil.login(123)
        return result200("ok")
    }

    @GetMapping("/test3")
    fun test3(): MR {
        return Mono.just(1)
            .map {
                StpUtil.login(it)
                result200(StpUtil.getTokenInfo())
            }
    }
}