package com.pro.blog.controller.TestController

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery

class T(
    val a: Int
)

@DgsComponent
class TestController {

    @DgsQuery
    fun test(input: T) = input.a

}