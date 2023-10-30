package com.pro.blog.pojo.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

data class Geography(
    val country: String,
    val province: String,
    val city: String?
)

@Document("user")
data class User constructor(
    @Id val id: String? = null,
    val avatar: String? = null,
    val userName: String? = null,
    var password: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val company: String? = null,
    val description: String? = null,
    val career: String? = null,
    val place: Geography? = null,
    val address: String? = null,
    var enterTime: String? = null,
    val personalPage: String? = null,
    val github: String? = null,
    // 关注
    val focus: List<String>? = null,
    // 被关注
    val focused: List<String>? = null,
    @Field()
    val deleted: Boolean? = false
)

