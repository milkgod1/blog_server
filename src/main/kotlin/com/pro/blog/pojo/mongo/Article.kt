package com.pro.blog.pojo.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("article")
data class Article(
    @Id val id: Long? = null,
    var uid: String? = null,
    // draft id
    var did: Long? = null,
    var content: String? = null,
    var coverImage: String? = null,
    var coverDescription: String? = null,
    var kindName: String? = null,
    var tagName: String? = null,
    var submitTime: String? = null,
    var title: String? = null,
    var see: Int = 0,
    var like: Int = 0,
    var commentId: List<String>? = null,
    // 专栏
    var columnId: List<String>? = null,
    // admin prove
    var checked: Boolean = false
)
