package com.pro.blog.service.impl

import com.pro.blog.dto.ArticlePage
import com.pro.blog.dto.ArticleSearchParam
import com.pro.blog.dto.Page
import com.pro.blog.pojo.es.Article
import com.pro.blog.repository.es.ArticleRepository
import com.pro.blog.service.ArticleService
import com.pro.blog.util.TimeOperation
import com.pro.blog.util.timestampMinus
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.script.Script
import org.elasticsearch.script.ScriptType
import org.elasticsearch.search.sort.ScriptSortBuilder
import org.elasticsearch.search.sort.SortBuilders
import org.elasticsearch.search.sort.SortOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchTemplate
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

const val seeCardinalNumber = 1000
const val likeCardinalNumber = 100
const val commentCardinalNumber = 80

const val scriptSortByHot = """
    doc['see'].value * params.see / $seeCardinalNumber +
    doc['like'].value * params.like / $likeCardinalNumber + 
    doc['commentId'].value.length * params.comment / $commentCardinalNumber 
"""

const val scriptSortByTime = scriptSortByHot.plus("""
    + doc['submitTime'].value * params.time * 1e-20
""")

@Service
class ArticleServiceImpl: ArticleService {

    val params = mapOf("see" to 0.3, "like" to 0.4, "comment" to 0.4, "time" to 0.1)
    @Autowired
    private lateinit var articleRepository: ArticleRepository

    @Autowired(required = false)
    private lateinit var reactiveElasticsearchTemplate: ReactiveElasticsearchTemplate

    override fun getArticleById(id: String): Mono<Article> = articleRepository.getArticleById(id)

    override fun getArticleByQuery(param: ArticleSearchParam): Mono<ArticlePage> {
        val page = PageRequest.of(param.page, param.size)
        val qb = QueryBuilders.boolQuery()
            .must(
                QueryBuilders.matchQuery("content", param.query)
            )
        when (param.period) {
            1 -> {
                qb.filter(
                    QueryBuilders.rangeQuery("submitTime").gte(timestampMinus(TimeOperation.DAY, 1))
                )
            }
            2 -> {
                qb.filter(
                    QueryBuilders.rangeQuery("submitTime").gte(timestampMinus(TimeOperation.WEEK, 1))
                )
            }
            3 -> {
                qb.filter(
                    QueryBuilders.rangeQuery("submitTime").gte(timestampMinus(TimeOperation.MONTH, 1))
                )
            }
            4 -> {
                qb.filter(
                    QueryBuilders.rangeQuery("submitTime").gte(timestampMinus(TimeOperation.MONTH, 3))
                )
            }
            else -> {
                // Time not limited
            }
        }

        val scriptSort = SortBuilders.scriptSort(Script(ScriptType.INLINE, "painless", scriptSortByHot, params),
            ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.DESC)
        val timeSort = SortBuilders.fieldSort("submitTime").order(SortOrder.DESC)
        val scriptTime = SortBuilders.scriptSort(Script(ScriptType.INLINE, "painless", scriptSortByTime, params),
            ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.DESC)

        val queryBuilder = NativeSearchQueryBuilder()
            .withQuery(qb)


        when (param.sort) {
            1 -> {
                // time
                queryBuilder.withSorts(timeSort)
            }
            2 -> {
                // like
                queryBuilder.withSorts(scriptSort)
            }
            else -> {
                queryBuilder.withSorts(scriptTime)
            }
        }

        val articleJavaClass = Article::class.java
        val countQuery = queryBuilder.build()
        val query = queryBuilder
            .withPageable(page)
            .build()

        return reactiveElasticsearchTemplate
            .search(query, articleJavaClass)
            .map {
                it.content
            }
            .collectList()
            .flatMap { articles ->
                reactiveElasticsearchTemplate
                    .count(countQuery, articleJavaClass)
                    .map {
                        ArticlePage(articles = articles, pageInfo = Page(page = param.page, size = param.size, count = it))
                    }
            }
    }

    override fun getArticle(article: Article): Flux<Article> {
        TODO("Not yet implemented")
    }


}
