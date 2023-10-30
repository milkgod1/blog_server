package com.pro.blog.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

typealias R = ResponseEntity<Any>
typealias M = Mono<Any>
typealias F = Flux<Any>
typealias MR = Mono<R>
typealias FR = Flux<R>

fun response(data: Any?): Any {
    val response = object {
        var msg: String? = null
        var data: Any? = null
    }
    if (data is String) {
        response.msg = data
    } else {
        response.data = data
    }
    return response
}

fun result400(msg: String? = null) = ResponseEntity<Any>(response(msg), HttpStatus.BAD_REQUEST)

fun result200(msg: String? = null) = ResponseEntity<Any>(response(msg), HttpStatus.OK)

fun result401(msg: String? = null) = ResponseEntity<Any>(response(msg), HttpStatus.UNAUTHORIZED)

fun result500(msg: String? = null) = ResponseEntity<Any>(response(msg), HttpStatus.INTERNAL_SERVER_ERROR)

fun result400(data: Any? = null) = ResponseEntity<Any>(response(data), HttpStatus.BAD_REQUEST)

fun result200(data: Any? = null) = ResponseEntity<Any>(response(data), HttpStatus.OK)

fun result401(data: Any? = null) = ResponseEntity<Any>(response(data), HttpStatus.UNAUTHORIZED)

fun result500(data: Any? = null) = ResponseEntity<Any>(response(data), HttpStatus.INTERNAL_SERVER_ERROR)

fun result404(msg: String? = null) = ResponseEntity<Any>(response(msg), HttpStatus.NOT_FOUND)

fun <T> monoResult(data: T) = Mono.just(data!!)

fun <T> fluxResult(data: T) = Flux.just(data!!)
