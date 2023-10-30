package com.pro.blog.config

//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.web.cors.CorsConfiguration
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource
//import org.springframework.web.filter.CorsFilter
//
//@Configuration
//class CorsConfig {
//    @Bean
//    fun corsFilter(): CorsFilter {
//        //1.添加Cors配置信息
//        val corsConfiguration = CorsConfiguration()
//        corsConfiguration.addAllowedOrigin("http://localhost:8000") //设置允许跨域访问服务端的客户端地址
//        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL) //设置允许的请求方式
//        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL) //设置访问哪些请求头
//        corsConfiguration.allowCredentials = true //允许携带cookie跨域
//
//        val u = UrlBasedCorsConfigurationSource()
//        u.registerCorsConfiguration("/**", corsConfiguration)
//        return CorsFilter(u)
//    }
//}