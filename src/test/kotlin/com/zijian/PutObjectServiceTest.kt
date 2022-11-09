package com.zijian

import io.mockk.*
import io.mockk.impl.annotations.MockK
import mu.KLogger
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.nio.file.Path

internal class PutObjectServiceTest {
    @MockK
    private lateinit var logger: KLogger

    @MockK
    private lateinit var s3Client: S3Client

    @MockK
    private lateinit var builder: PutObjectRequest.Builder

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun put() {
        val slot = slot<() -> Any?>()
        every { logger.info(capture(slot)) } just Runs
        mockkStatic(PutObjectRequest::class)
        mockkStatic(RequestBody::class)
        every { PutObjectRequest.builder() } returns builder
        every { builder.bucket("") } returns builder
        every { builder.key("") } returns builder
        every { builder.build() } returns null
        every { RequestBody.fromFile(any<Path>()) } returns null
        every { s3Client.putObject(any<PutObjectRequest>(), any<RequestBody>()) } returns null

        val service = PutObjectService(logger)
        service.s3Client = s3Client
        service.put("aa")

        Assertions.assertEquals("~~~~~~~~~~~~~~~~~~S3 saving~~~~~~~~~~~~", slot.captured.invoke())
    }
}