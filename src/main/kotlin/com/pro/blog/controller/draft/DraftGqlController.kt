package com.pro.blog.controller.draft

import cn.dev33.satoken.stp.StpUtil
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import com.pro.blog.annotation.LoginCheck
import com.pro.blog.dto.CreatorDraftQuery
import com.pro.blog.dto.CreatorDraftQueryResult
import com.pro.blog.pojo.mongo.Draft
import com.pro.blog.service.DraftService
import org.springframework.beans.factory.annotation.Autowired

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@DgsComponent
class DraftGqlController {

    @Autowired
    lateinit var draftService: DraftService

    @DgsQuery
    fun getEditor(@InputArgument id: String): Mono<Draft> {
        StpUtil.checkLogin()
        return draftService
            .getDraftByDraftId(id)
            .log()
    }

    @DgsQuery
    fun getAllDraft(): Flux<Draft> {
        return draftService
            .getDrafts(StpUtil.getLoginIdAsString())
            .log()
    }

    @DgsQuery
    fun draftQuery(@InputArgument query: CreatorDraftQuery): Mono<CreatorDraftQueryResult> {
        return draftService
            .getPageableDraft(query, StpUtil.getLoginIdAsString())
            .log()
    }

    @DgsMutation
    fun publishDraft(@InputArgument draft: Draft): Mono<String> {
        return draftService
            .publishDraft(draft)
            .log()
    }
}