package com.pro.blog

import com.pro.blog.dto.CreatorDraftQuery
import com.pro.blog.repository.mongo.DraftRepository
import com.pro.blog.repository.mongo.UserRepository
import com.pro.blog.service.DraftService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate


@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private lateinit var reactiveElasticsearchTemplate: ReactiveElasticsearchTemplate

    @Autowired
    private lateinit var draftRepository: DraftRepository

    @Autowired
    private lateinit var draftService: DraftService

    @Test
    fun contextLoads() {
//        Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13)
//            .flatMap {
//                draftService.draftOperation(
//                    Draft(createTime = System.currentTimeMillis(),
//                        content = "<p>".plus("content").plus(it).plus("</p>"), uid = "62a447bc7adab5319e0df8ba")
//                )
//            }
//            .subscribe()

        draftService.getPageableDraft(
            query = CreatorDraftQuery(page = 1, title = ""),
            uid = "62a447bc7adab5319e0df8ba"
        ).subscribe {
            it.drafts?.forEach { draft ->
                println(draft)
            }
            println(it.pageInfo)
        }

    }

}
