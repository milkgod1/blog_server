package com.pro.blog.repository.mongo

import com.pro.blog.pojo.mongo.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface UserRepository: ReactiveMongoRepository<User, String> {
    fun findByUserNameAndPassword(name: String, password: String): Mono<User>

    fun findUserById(id: String): Mono<User>

    fun findByUserName(name: String): Flux<User>

}