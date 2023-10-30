package com.pro.blog.service

import com.pro.blog.dto.ArticlePage
import com.pro.blog.dto.ArticleSearchParam
import com.pro.blog.dto.OrderBy
import com.pro.blog.pojo.es.Article
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ArticleService {

    fun getArticleById(id: String): Mono<Article>

    fun getArticleByQuery(param: ArticleSearchParam): Mono<ArticlePage>

    fun getArticle(article: Article): Flux<Article>

}