package com.pro.blog.pojo.es

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.annotation.Transient
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "article")
data class Article(
    @Id
    var id: String? = null,
    @Field(type = FieldType.Keyword)
    var uid: String? = null,
    // draft id
    @Field(type = FieldType.Keyword)
    var did: String? = null,

    @Field(index = false, type = FieldType.Text)
    var content: String? = null,

    @Field(index = false, type = FieldType.Text)
    var coverImage: String? = null,

    @Field(index = false, type = FieldType.Text)
    var coverDescription: String? = null,

    @Field(type = FieldType.Keyword)
    var kindId: String? = null,

    @Field(type = FieldType.Keyword)
    var tagId: String? = null,

    @Field
    var submitTime: Long? = null,

    @Field(type = FieldType.Keyword)
    var title: String? = null,

    @Field(type = FieldType.Integer)
    var see: Int = 0,

    @Field(type = FieldType.Integer)
    var like: Int = 0,

    @Field
    var commentId: List<String>? = null,
    // 专栏
    @Field
    var columnId: List<String>? = null,
)
