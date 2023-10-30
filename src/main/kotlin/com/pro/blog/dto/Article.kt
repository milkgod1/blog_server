package com.pro.blog.dto

import com.pro.blog.pojo.es.Article

enum class OrderBy(
    val orderBy: String
) {
    Time("time"),
    Hot("hot"),
    Recommend("recommend")
}

class ArticleSearchParam(
    val page: Int = 0,
    val size: Int = 0,
    val sort: Int = 0,
    val query: String? = null,
    val period: Int = 0
)

class ArticlePage(
    val articles: MutableList<Article>? = null,
    val pageInfo: Page = Page()
)