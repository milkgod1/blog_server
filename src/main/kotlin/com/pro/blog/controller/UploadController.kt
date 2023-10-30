package com.pro.blog.controller

import com.pro.blog.exception.UploadBadRequest
import com.pro.blog.pojo.mongo.Draft
import com.pro.blog.service.DraftService
import com.pro.blog.util.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Mono
import java.io.File
import java.util.concurrent.CompletableFuture


@RestController
@RequestMapping("/api/upload")
class UploadController {

    @Autowired
    lateinit var draftService: DraftService

    @Value("\${upload.article}")
    lateinit var uploadUrl: String

    @PostMapping("/img")
    fun imageUpload(@RequestParam("cover") cover: MultipartFile, @RequestParam(name = "id", required = false) id: String?): MR {
        val imgType = when (cover.contentType) {
            "image/png" -> {"png"}
            "image/jpeg" -> {"jpg"}
            else -> {throw UploadBadRequest("type not satisfied")}
        }
        val imgPath = uploadUrl.plus(id ?: System.currentTimeMillis()).plus(".$imgType")
        val file = File(imgPath)
        return Mono.fromFuture(CompletableFuture.supplyAsync {
            if (file.exists()) {
                file.delete()
            }
            cover.transferTo(file)
            true
        }).flatMap {
            draftService.draftOperation(Draft(id = id, coverImage = imgPath))
        }.map {
            result200(object {
                val aid = it
            })
        }
    }

}