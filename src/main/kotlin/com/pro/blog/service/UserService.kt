package com.pro.blog.service

import com.pro.blog.pojo.mongo.User
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserService {
    fun canRegister(user: User): Mono<Boolean>

    fun userLoginByNameAndPassword(name: String, password: String): Mono<User>

    fun userRegister(user: User): Mono<User>

    fun userInfoById(id: String): Mono<User>

    fun findUser(user: User): Flux<User>

    fun findUserByName(name: String): Flux<User>
}