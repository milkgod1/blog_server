package com.pro.blog.pojo.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("comment")
data class Comment(
    @Id val id: String? = null,
    val cid: String? = null,
    val aid: String? = null,
    val uid: String? = null,
    val content: String? = null,
    val submitTime: String? = null,
    val like: Int? = null,
    val child: List<ChildComment>? = null
)

data class ChildComment(
    val cid: String? = null,
    // reply cid
    val replyId: String? = null,
    val uid: String? = null,
    val content: String? = null,
    val like: Int? = null
)