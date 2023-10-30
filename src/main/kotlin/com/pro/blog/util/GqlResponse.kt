package com.pro.blog.util

class GqlResponse(
    val code: Number,
    val msg: String? = null,
    val data: Any? = null
)

fun gql200(data: Any?) = GqlResponse(200, data = data)
fun gql200(msg: String?) = GqlResponse(200, msg)


