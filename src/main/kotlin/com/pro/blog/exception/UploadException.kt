package com.pro.blog.exception

import com.pro.blog.util.R
import com.pro.blog.util.result400
import com.pro.blog.util.result500
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.IOException

@RestControllerAdvice
class UploadException {
    @ExceptionHandler(UploadBadRequest::class)
    fun badRequest(e: Exception): R {
        return result400(e.message)
    }

    @ExceptionHandler(IOException::class)
    fun storeImageError(): R{
        return result500("io error with server")
    }
}

class UploadBadRequest(private val msg: String): Exception(msg)