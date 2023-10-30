package com.pro.blog.aspect

import cn.dev33.satoken.stp.StpUtil
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class UserAspect {

    companion object {
        private const val loginCheck: String = "@annotation(com.pro.blog.annotation.LoginCheck) || @within(com.pro.blog.annotation.LoginCheck)"
    }

    @Before(loginCheck)
    fun checkUserLogin() {
        StpUtil.checkLogin()
    }

}