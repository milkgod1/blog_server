package com.pro.blog.repository.mongo

import com.pro.blog.pojo.mongo.Article
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

//@Repository
//interface ArticleRepository: ReactiveMongoRepository<Article, String> {
//    fun getArticleById(id: String): Mono<Article>
//}