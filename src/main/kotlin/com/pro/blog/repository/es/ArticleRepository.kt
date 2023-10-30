package com.pro.blog.repository.es

import com.pro.blog.pojo.es.Article
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface ArticleRepository: ReactiveElasticsearchRepository<Article, Long> {

    fun getArticleById(id: String): Mono<Article>

}