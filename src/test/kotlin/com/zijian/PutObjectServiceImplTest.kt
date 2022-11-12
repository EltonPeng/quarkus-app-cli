package com.zijian

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.quarkus.test.junit.QuarkusTest
import mu.KLogger
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.lang.reflect.Constructor
import java.nio.file.Path

internal class PutObjectServiceImplTest {
    @MockK
    private lateinit var logger: KLogger

    @MockK
    private lateinit var s3Client: S3Client

    @MockK
    private lateinit var builder: PutObjectRequest.Builder

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `cover constructors`() {
        val constructors = PutObjectServiceImpl::class.java.constructors
        assertEquals(2, constructors.count())
        constructors.forEach {
            if(it.parameters.count() == 2){
                Assertions.assertNotNull(it.newInstance(logger, s3Client))
            }
            else {
                assertNotNull(it.newInstance(logger, s3Client, 0, null))
                assertNotNull(it.newInstance(logger, s3Client, 1, null))
            }
        }
    }

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

        val service = PutObjectServiceImpl(logger, s3Client)
        service.put("aa")

        Assertions.assertEquals("~~~~~~~~~~~~~~~~~~S3 saving~~~~~~~~~~~~", slot.captured.invoke())
    }
}