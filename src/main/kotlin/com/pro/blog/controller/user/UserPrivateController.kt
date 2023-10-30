package com.pro.blog.controller.user

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.stp.StpUtil
import com.pro.blog.exception.customer.BadRequestException
import com.pro.blog.service.UserService
import com.pro.blog.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user/pri")
class UserPrivateController constructor(
    @Autowired private val userService: UserService
) {
    @GetMapping("/info")
    fun retrieveUserInfo(): MR {
        return userService.userInfoById(StpUtil.getLoginIdAsString())
            .log()
            .map { result200(it) }
            .defaultIfEmpty(result400("user can not find"))
    }

    @GetMapping("/logout")
    fun logout(): R {
        // If not login it can not log out -> throw error
        val uid = StpUtil.getLoginId()
        StpUtil.logout(uid)
        return result200("logout successfully")
    }



}