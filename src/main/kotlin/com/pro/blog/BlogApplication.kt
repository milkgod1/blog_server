package com.pro.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class BlogApplication

fun main(args: Array<String>) {
    runApplication<BlogApplication>(*args)
}
