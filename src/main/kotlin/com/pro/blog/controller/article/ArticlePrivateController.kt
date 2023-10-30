package com.pro.blog.controller.article

import cn.dev33.satoken.stp.StpUtil
import com.pro.blog.pojo.mongo.Article
import com.pro.blog.service.ArticleService
import com.pro.blog.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/article/pri")
class ArticlePrivateController {

    @Autowired
    lateinit var articleService: ArticleService

    @GetMapping("/post")
    fun getPersonalDrafts(): R {
        return result200("")
    }

}