package com.pro.blog.service.impl

import com.pro.blog.dto.*
import com.pro.blog.exception.customer.BadRequestException
import com.pro.blog.pojo.es.Article
import com.pro.blog.pojo.mongo.Draft
import com.pro.blog.repository.es.ArticleRepository
import com.pro.blog.repository.mongo.DraftRepository
import com.pro.blog.service.DraftService
import com.pro.blog.util.objectToMap
import org.elasticsearch.action.update.UpdateRequest
import org.elasticsearch.index.reindex.UpdateByQueryRequest
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate
import org.springframework.data.elasticsearch.core.query.UpdateQuery
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import util.SnowflakeUtil

@Transactional
@Service
class DraftServiceImpl: DraftService {

    @Autowired
    private lateinit var draftRepository: DraftRepository

    @Autowired
    private lateinit var articleRepository: ArticleRepository

    @Autowired
    private lateinit var reactiveMongoTemplate: ReactiveMongoTemplate

    @Autowired(required = false)
    private lateinit var reactiveElasticsearchTemplate: ReactiveElasticsearchTemplate

    override fun draftOperation(draft: Draft): Mono<Draft> {
//        val query = Query()
//        if (draft.id != null) {
//            query.addCriteria(Criteria.where("id").`is`(draft.id))
//        } else {
//            // new draft
//            draft.id = SnowflakeUtil().generateId().toString()
//            draft.createTime = System.currentTimeMillis()
//        }
//        val update = Update()
//        objectToMap(draft).forEach { (k, v) ->
//            update.set(k, v)
//        }
//        return reactiveMongoTemplate
//            .upsert(query, update, Draft::class.java)
//            .log()
//            .flatMap {
//                val id = it.upsertedId.toString()
//                draftRepository
//                    .getDraftById(id)
//                    .log()
//            }

        if (draft.id != null) {
            val query = Query()
            val update = Update()
            query.addCriteria(Criteria.where("id").`is`(draft.id))
            objectToMap(draft).forEach { (k, v) ->
                update.set(k, v)
            }
            return reactiveMongoTemplate
                .updateFirst(query, update, Draft::class.java)
                .log()
                .flatMap {
                    draftRepository
                        .getDraftById(draft.id!!)
                        .log()
                }
        } else {
            // new draft
            draft.id = SnowflakeUtil().generateId().toString()
            draft.createTime = System.currentTimeMillis()
            return draftRepository
                .insert(draft)
        }
    }

    override fun getDraftByDraftId(id: String): Mono<Draft> = draftRepository.getDraftById(id)

    override fun getDrafts(uid: String): Flux<Draft> = draftRepository.getDraftByUidOrderByCreateTimeDesc(uid)

    override fun getPageableDraft(query: CreatorDraftQuery, uid: String): Mono<CreatorDraftQueryResult> {
        val page = PageRequest.of(query.page, query.size)
        val sort = Sort.by(Sort.Direction.DESC, "createTime")

        val q = Query.query(
            Criteria.where("uid").`is`(uid)
        ).allowSecondaryReads()

        query.title?.let {
            if (it != "") {
                q.addCriteria(
                    Criteria.where("title").regex(it)
                )
            }
        }

        q.with(sort)
        val countQuery = Query.of(q)
        q.with(page)

        val draftJavaClass = Draft::class.java
        return reactiveMongoTemplate
            .find(q, draftJavaClass)
            .collectList()
            .flatMap { creatorDrafts ->
                reactiveMongoTemplate
                    .count(countQuery, draftJavaClass)
                    .map {
                        CreatorDraftQueryResult(
                            drafts = creatorDrafts,
                            pageInfo = Page(page = query.page, size = query.size, count = it)
                        )
                    }
            }
    }

    override fun publishDraft(draft: Draft): Mono<String> {
        if (draft.kindId == null
            || draft.content == null
            || draft.tagId == null
            || draft.title == null
        ) throw BadRequestException("article must have kine tag title and content")

        val article = Article()
        BeanUtils.copyProperties(draft, article)
        if (draft.aid == null) {
            // first publish
            article.id = SnowflakeUtil().generateId().toString()
            article.did = draft.id
            article.submitTime = System.currentTimeMillis()

            return reactiveElasticsearchTemplate
                .save(article)
                .mapNotNull {
                    it.id
                }
        } else {
            val query = Query()
            query.addCriteria(Criteria.where("id").`is`(draft.aid))

            article.id = null
            article.submitTime = System.currentTimeMillis()

            val update = Update()
            objectToMap(article).forEach { (k, v) ->
                update.set(k, v)
            }
            return reactiveMongoTemplate
                .update(query, update, Article::class.java)
                .log()
                .map {
                    it.upsertedId?.asString().toString()
                }
        }
    }

}