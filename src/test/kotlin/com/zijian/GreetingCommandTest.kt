package com.zijian

import io.mockk.*
import io.mockk.impl.annotations.MockK
import mu.KLogger
import org.eclipse.microprofile.rest.client.RestClientBuilder
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class GreetingCommandTest {

    @MockK
    private lateinit var logger: KLogger

    @MockK
    private lateinit var putObjectService: PutObjectService

    @MockK
    private lateinit var convertService: ConvertService

    @MockK
    private lateinit var tokenService: TokenService

    @MockK
    private lateinit var restClientFactory: RestClientFactory

    @MockK
    private lateinit var restClientBuilder: RestClientBuilder

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `cover constructors`() {
        val constructors = GreetingCommand::class.java.constructors
        constructors.forEach {
            if (it.parameters.isEmpty()) {
                assertNotNull(it.newInstance())
            } else if (it.parameters.count() == 1) {
                assertNotNull(it.newInstance(logger))
            } else {
                assertNotNull(it.newInstance(logger, 0, null))
                assertNotNull(it.newInstance(logger, 1, null))
            }
        }
    }

    @Test
    fun run() {
        val logMessages = mutableListOf<String>()
        val slot = slot<() -> Any?>()
        every { logger.info(capture(slot)) } answers { logMessages.add(slot.captured.invoke().toString()) }
        every { putObjectService.put(any()) } just Runs
        every { convertService.goWithMoshi(any()) } returns ""
        //every { tokenService.get() } returns Token("you", "", LocalDate.EPOCH, TokenType.Temp)

        mockkStatic(RestClientBuilder::class)
        every { RestClientBuilder.newBuilder() } returns restClientBuilder
        every { restClientBuilder.baseUri(any()) } returns restClientBuilder
        every {
            restClientBuilder.build(
                Class.forName("com.zijian.TokenService")
            )
        } returns tokenService

        val command = GreetingCommand(logger)
        command.restClientFactory = restClientFactory
        command.convertService = convertService
        command.putObjectService = putObjectService
        command.run()

        assertTrue(logMessages[0].contains("token is retrieved from class com.zijian.RestClientFactory"))
        verify { putObjectService.put(any()) }
        assertEquals("~~~~~~~~~~~~~~~~~~logging~~~~~~~~~~~~", logMessages[2])

        unmockkStatic(RestClientBuilder::class)
    }
}