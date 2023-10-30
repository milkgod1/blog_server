package com.pro.blog.pojo.mongo

import org.springframework.data.annotation.Id

data class Kind(
    @Id
    val id: String? = null,

    val kindName: String? = null
)
