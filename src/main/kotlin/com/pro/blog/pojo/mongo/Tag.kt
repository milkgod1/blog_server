package com.pro.blog.pojo.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("tag")
data class Tag(
    @Id val id: String? = null,
    val tagName: String? = null,
)
