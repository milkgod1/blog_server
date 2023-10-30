package com.pro.blog.exception

import cn.dev33.satoken.exception.NotLoginException
import com.pro.blog.util.R
import com.pro.blog.util.result401
import com.pro.blog.util.result500
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserException {


    @ExceptionHandler(NotLoginException::class)
    fun notLoginHandler(): R {
        return result401("please login")
    }

    @ExceptionHandler(NullPointerException::class)
    fun nullException(): R = result500("server error")

}