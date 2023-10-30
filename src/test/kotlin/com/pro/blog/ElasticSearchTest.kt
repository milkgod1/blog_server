package com.pro.blog

import com.pro.blog.pojo.es.Article
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.reindex.UpdateByQueryRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.elasticsearch.client.reactive.ReactiveElasticsearchClient
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import util.SnowflakeUtil
import java.util.stream.IntStream
import java.util.stream.Stream
import javax.annotation.PostConstruct
import kotlin.streams.toList

@SpringBootTest
class ElasticSearchTest {

    @Autowired
    final lateinit var reactiveElasticsearchClient: ReactiveElasticsearchClient

    private lateinit var reactiveElasticsearchTemplate: ReactiveElasticsearchTemplate

    @PostConstruct
    fun init() {
        reactiveElasticsearchTemplate = ReactiveElasticsearchTemplate(reactiveElasticsearchClient)
    }

    @Test
    fun addArticles() {
//        val l = Stream.of(1, 2, 3, 4, 5, 6)
//            .map {
//                Article(
//                    id = SnowflakeUtil().generateId().toString(),
//                    title = "title".plus(it),
//                    content = "<p>".plus("content").plus(it).plus()
//                )
//            }
//            .toArray()

//        val q = BoolQueryBuilder()
//            .must(
//                QueryBuilders.idsQuery().addIds("1536378056707538949")
//            )
//
//        val query = UpdateByQueryRequest()
//            .setQuery(
//                BoolQueryBuilder()
//                    .must(
//                        QueryBuilders.idsQuery().addIds("1536378056707538949")
//                    )
//            )
//
//        reactiveElasticsearchTemplate.search(NativeSearchQueryBuilder().withQuery(q).build(), Article::class.java)
//            .subscribe {
//                println(it)
//            }

    }

}