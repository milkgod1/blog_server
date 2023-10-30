package com.pro.blog.controller.article

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import com.pro.blog.dto.ArticlePage
import com.pro.blog.dto.ArticleSearchParam
import com.pro.blog.pojo.mongo.Article
import com.pro.blog.service.ArticleService
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@DgsComponent
class ArticleGqlController {

    @Autowired
    lateinit var articleService: ArticleService

    @DgsQuery
    fun getArticleList(@InputArgument p: ArticleSearchParam): Mono<ArticlePage> {
        return articleService
            .getArticleByQuery(p)
            .log()
    }

}