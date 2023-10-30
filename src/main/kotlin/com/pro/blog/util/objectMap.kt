package com.pro.blog.util

import kotlin.reflect.full.memberProperties

fun objectToMap(data: Any): MutableMap<String, Any> {
    val map = mutableMapOf<String, Any>()
    data::class.memberProperties.forEach {
        val name = it.name

        if (name != "iewrwerd") {
            val value = it.call(data)
            value?.let {
                map[name] = value
            }
        }

    }
    return map
}
