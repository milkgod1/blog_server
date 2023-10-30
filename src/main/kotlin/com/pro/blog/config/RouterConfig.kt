package com.pro.blog.config

import cn.dev33.satoken.`fun`.SaParamFunction
import cn.dev33.satoken.reactor.filter.SaReactorFilter
import cn.dev33.satoken.router.SaRouter
import cn.dev33.satoken.router.SaRouterStaff
import cn.dev33.satoken.stp.StpUtil
import com.pro.blog.util.result401
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouterConfig {
    @Bean
    fun getSaReactorFilter(): SaReactorFilter {
        return SaReactorFilter()
            .addInclude("/**")
            .setAuth {
                SaRouter.match("/api/*/pri/**")
                    .check { _ ->
                        StpUtil.checkLogin()
                    }
                SaRouter.match("/api/draft/**")
                    .check { _ ->
                        StpUtil.checkLogin()
                    }
            }
    }
}
