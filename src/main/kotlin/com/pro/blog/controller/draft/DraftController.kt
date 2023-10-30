package com.pro.blog.controller.draft

import cn.dev33.satoken.stp.StpUtil
import com.pro.blog.pojo.mongo.Draft
import com.pro.blog.service.DraftService
import com.pro.blog.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/draft")
class DraftController {

    @Autowired
    lateinit var draftService: DraftService

    @PostMapping("/new")
    fun draft(@RequestBody draft: Draft): MR {
        draft.aid = null
        draft.uid = StpUtil.getLoginIdAsString()
        return draftService
            .draftOperation(draft)
            .log()
            .map {
                result200(object {
                    val id = it.id
                })
            }
            .defaultIfEmpty(result500("server wrong"))
    }

    @PostMapping("/update")
    fun updateDraft(@RequestBody draft: Draft): MR {
        draft.uid = StpUtil.getLoginIdAsString()

        val updateId = draft.id?.let {
            draftService.draftOperation(draft)
        }

        return updateId
            ?.map {
                result200(it)
            }
            ?.defaultIfEmpty(result500("server wrong"))
            ?: monoResult(result400("article id must provide"))
    }


//    @GetMapping("/fetch/{id}")
//    fun getEditor(@PathVariable("id") id: String): MR {
//        return draftService
//            .getDraftByDraftId(id)
//            .map {
//                result200(it)
//            }
//            .defaultIfEmpty(result404("Not found such article"))
//    }
//
//    @GetMapping("/all")
//    fun getPersonalDraftList(): FR {
//        val uid = StpUtil.getLoginIdAsString()
//        return draftService
//            .getDrafts(uid)
//            .map {
//                result200(it)
//            }
//    }
}