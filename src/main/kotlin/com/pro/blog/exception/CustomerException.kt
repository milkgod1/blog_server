package com.pro.blog.exception

import com.pro.blog.exception.customer.BadRequestException
import com.pro.blog.exception.customer.NotfoundException
import com.pro.blog.exception.customer.UnAuthorizedException
import com.pro.blog.util.R
import com.pro.blog.util.result400
import com.pro.blog.util.result401
import com.pro.blog.util.result404
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomerException {

    @ExceptionHandler(BadRequestException::class)
    fun badRequest(e: Exception): R = result400(e.message)

    @ExceptionHandler(NotfoundException::class)
    fun notFound(e: Exception): R = result404(e.message)

    @ExceptionHandler(UnAuthorizedException::class)
    fun unAuthorized(e: Exception): R = result401(e.message)
}