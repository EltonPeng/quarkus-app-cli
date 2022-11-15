package com.zijian

import io.mockk.*
import io.mockk.impl.annotations.MockK
import mu.KLogger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GreetingCommandTest {

    @MockK
    private lateinit var logger: KLogger

    @MockK
    private lateinit var putObjectService: PutObjectService

    @MockK
    private lateinit var tokenService: TokenService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `cover constructors`() {
        val constructors = GreetingCommand::class.java.constructors
        constructors.forEach {
            if(it.parameters.isEmpty()) {
                assertNotNull(it.newInstance())
            } else if(it.parameters.count() == 2){
                assertNotNull(it.newInstance(logger, tokenService))
            }
            else {
                assertNotNull(it.newInstance(logger, tokenService, 0, null))
                assertNotNull(it.newInstance(logger, tokenService, 1, null))
            }
        }
    }

    @Test
    fun run() {
        val logMessages = mutableListOf<String>()
        val slot = slot<() -> Any?>()
        every { logger.info(capture(slot)) } answers { logMessages.add(slot.captured.invoke().toString()) }
        every { putObjectService.put(any()) } just Runs
        every { tokenService.get() } returns Token("you", "guess")


        val command = GreetingCommand(logger, tokenService)
//        command.tokenService = tokenService
        command.putObjectService = putObjectService
        command.run()

        assertEquals("token is you", logMessages[0])
        verify { putObjectService.put(any()) }
        assertEquals("~~~~~~~~~~~~~~~~~~logging~~~~~~~~~~~~", logMessages[1])
    }
}