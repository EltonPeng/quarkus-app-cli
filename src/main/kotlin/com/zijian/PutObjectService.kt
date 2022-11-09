package com.zijian


@ApplicationScoped
class PutObjectService (
    private val logger: KLogger = KotlinLogging.logger {}
){

    @Inject
    lateinit var s3Client: S3Client

    fun put(filePath: String) {
        logger.info { "~~~~~~~~~~~~~~~~~~S3 saving~~~~~~~~~~~~" }
    }
}