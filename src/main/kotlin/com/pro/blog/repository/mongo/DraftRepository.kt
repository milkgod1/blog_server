package com.pro.blog.repository.mongo

import com.pro.blog.pojo.mongo.Draft
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface DraftRepository: ReactiveMongoRepository<Draft, String> {
    fun getDraftById(id: String): Mono<Draft>
    fun getDraftByUidOrderByCreateTimeDesc(uid: String): Flux<Draft>
}