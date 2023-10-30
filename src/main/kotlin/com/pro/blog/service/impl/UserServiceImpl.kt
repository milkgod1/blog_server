package com.pro.blog.service.impl

import com.pro.blog.pojo.mongo.User
import com.pro.blog.repository.mongo.UserRepository
import com.pro.blog.service.UserService
import com.pro.blog.util.md5
import com.pro.blog.util.toHex
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val passwordReg =
    Regex("^(?!^[0-9]+\$)(?!^[a-z]+\$)(?!^[A-Z]+\$)(?!^[0-9a-z]+\$)(?!^[0-9A-Z]+\$)(?!^[a-zA-Z]+\$)[a-z0-9A-Z]{8,15}\$")
val uriReg = Regex("^https?://([a-zA-Z0-9]+\\.+)+(org|cn|com|top).*\$")

@Service
class UserServiceImpl constructor(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val reactiveMongoTemplate: ReactiveMongoTemplate
) : UserService {

    override fun canRegister(user: User): Mono<Boolean> {
        val name = user.userName
        val password = user.password
        if (name != null) {
            if (password == null || !password.matches(passwordReg)) return Mono.just(false)
            return this.findUserByName(name)
                .log()
                .any {
                    true
                }
        }
        else if (user.email == null && user.phone == null) return Mono.just(false)
        return Mono.just(true)
    }

    override fun userLoginByNameAndPassword(name: String, password: String): Mono<User> {
        return userRepository.findByUserNameAndPassword(name, password.md5().toHex())
    }

    override fun userRegister(user: User): Mono<User> {
        user.password = user.password?.md5()?.toHex()
        user.enterTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return userRepository.insert(user)
    }

    override fun userInfoById(id: String): Mono<User> = userRepository.findUserById(id)

    override fun findUser(user: User): Flux<User> {
        val matcher = ExampleMatcher.matching().withIgnoreNullValues()
        val ex: Example<User> = Example.of(user, matcher)
        return userRepository.findAll(ex)
    }

    override fun findUserByName(name: String): Flux<User> = userRepository.findByUserName(name)

}