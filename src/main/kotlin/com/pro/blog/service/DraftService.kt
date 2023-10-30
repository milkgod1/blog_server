package com.pro.blog.service

import com.pro.blog.dto.CreatorDraftQuery
import com.pro.blog.dto.CreatorDraftQueryResult
import com.pro.blog.pojo.mongo.Draft
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface DraftService {

    fun draftOperation(draft: Draft): Mono<Draft>

    fun getDraftByDraftId(id: String): Mono<Draft>

    fun getDrafts(uid: String): Flux<Draft>

    fun getPageableDraft(query: CreatorDraftQuery, uid: String): Mono<CreatorDraftQueryResult>

    fun publishDraft(draft: Draft): Mono<String>

}