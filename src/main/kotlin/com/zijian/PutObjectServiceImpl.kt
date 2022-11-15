package com.zijian

import mu.KLogger
import mu.KotlinLogging
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.nio.file.Path
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import kotlin.io.path.exists


@ApplicationScoped
class PutObjectServiceImpl (
    private val logger: KLogger = KotlinLogging.logger {}
) : PutObjectService {

    @Inject
    lateinit var s3Client: S3Client

    override fun put(filePath: String) {
        val path = Path.of(filePath)
        if(!path.exists()) {
            throw RuntimeException("file does not exist!")
        }

        val putObjectRequest = PutObjectRequest.builder()
            .bucket("")
            .key("")
            .build()
        val requestBody = RequestBody.fromFile(Path.of(filePath))

        s3Client.putObject(putObjectRequest, requestBody)
        logger.info { "~~~~~~~~~~~~~~~~~~S3 saving~~~~~~~~~~~~" }
    }
}