package com.pro.blog.config

import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.exception.SaTokenException
import com.pro.blog.exception.customer.BadRequestException
import org.springframework.boot.autoconfigure.web.WebProperties.Resources
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono


/**
 * For webflux error handler
 */

@Configuration
class ExceptionBean {
    @Bean
    fun resources(): Resources? {
        return Resources()
    }
}

@Component
@Order(-2)
class GlobalExceptionHandler(
    errorAttributes: ErrorAttributes,
    resources: Resources,
    applicationContext: ApplicationContext,
    config: ServerCodecConfigurer,
) : AbstractErrorWebExceptionHandler(errorAttributes, resources, applicationContext) {
    init {
        this.setMessageReaders(config.readers)
        this.setMessageWriters(config.writers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> {

        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse)
    }

    private fun renderErrorResponse(request: ServerRequest): Mono<ServerResponse> {
        val map = mutableMapOf<String, Any>()
        val error = getError(request)
        var status = HttpStatus.INTERNAL_SERVER_ERROR
        val msg = error.message

        if (error is SaTokenException) {
            if (error.cause is NotLoginException) {
                status = HttpStatus.UNAUTHORIZED
                map["msg"] = "please login"
            }
        }

        if (error is BadRequestException) {
            status = HttpStatus.BAD_REQUEST
            map["msg"] = msg ?: "param support wrong"
        }

        return ServerResponse
            .status(status)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(map))
    }


}