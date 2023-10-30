package com.pro.blog.dto

import cn.dev33.satoken.stp.SaTokenInfo
import com.pro.blog.pojo.mongo.Geography
import com.pro.blog.pojo.mongo.User
import org.springframework.beans.BeanUtils

data class UserInfo(
    var id: String? = null,
    var avatar: String? = null,
    var userName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    var company: String? = null,
    var description: String? = null,
    var career: String? = null,
    var place: Geography? = null,
    var address: String? = null,
    var enterTime: String? = null,
    var personalPage: String? = null,
    val github: String? = null
) {
    companion object {

    }
}

fun UserInfo.Companion.toUserInfo(user: User): UserInfo {
    val userInfo = UserInfo()
    BeanUtils.copyProperties(user, userInfo)
    return userInfo
}

data class LoginInfo(
    val userInfo: User? = null,
    val tokenInfo: SaTokenInfo? = null,
)