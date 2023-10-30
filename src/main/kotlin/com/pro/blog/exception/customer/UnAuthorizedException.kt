package com.pro.blog.exception.customer

class UnAuthorizedException: Exception {

    constructor(): super()

    constructor(msg: String): super(msg)

    constructor(cause: Throwable): super(cause)

    constructor(msg: String, cause: Throwable): super(msg, cause)

}