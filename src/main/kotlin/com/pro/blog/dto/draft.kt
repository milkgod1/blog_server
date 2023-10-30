package com.pro.blog.dto

import com.pro.blog.pojo.mongo.Draft


class CreatorDraftQuery (
    val title: String? = null,
    val page: Int = 0,
    val size: Int = 10,
)
class CreatorDraftQueryResult(
    val drafts: MutableList<Draft>? = null,
    val pageInfo: Page = Page()
)