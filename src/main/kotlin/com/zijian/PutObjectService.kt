package com.zijian

import mu.KLogger
import mu.KotlinLogging
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.nio.file.Path
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject


@ApplicationScoped
class PutObjectService (
    private val logger: KLogger = KotlinLogging.logger {}
){

    @Inject
    lateinit var s3Client: S3Client

    fun put(filePath: String) {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket("")
            .key("")
            .build()
        val requestBody = RequestBody.fromFile(Path.of(filePath))

        s3Client.putObject(putObjectRequest, requestBody)
        logger.info { "~~~~~~~~~~~~~~~~~~S3 saving~~~~~~~~~~~~" }
    }
}