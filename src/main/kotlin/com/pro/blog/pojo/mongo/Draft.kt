package com.pro.blog.pojo.mongo

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("draft")
data class Draft(
    @Id var id: String? = null,
    // if published it will have an article id
    var aid: String? = null,
    var uid: String? = null,
    val title: String? = null,
    val content: String? = null,
    val kindId: String? = null,
    val tagId: String? = null,
    val coverImage: String? = null,
    val coverDescription: String? = null,
    var createTime: Long? = null,
    val editorType: String? = "richText",
)
