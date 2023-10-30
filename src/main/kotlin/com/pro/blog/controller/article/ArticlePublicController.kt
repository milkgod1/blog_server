package com.pro.blog.controller.article

import com.pro.blog.util.R
import com.pro.blog.util.result200
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/article/pub")
class ArticlePublicController {
    @GetMapping("test")
    fun test(): R = result200("111")

}