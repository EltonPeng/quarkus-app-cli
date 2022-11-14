package com.zijian

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.quarkus.test.junit.QuarkusTest
import mu.KLogger
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.lang.reflect.Constructor

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
            if(it.parameters.count() == 3){
                Assertions.assertNotNull(it.newInstance(logger, tokenService, putObjectService))
            }
            else {
                Assertions.assertNotNull(it.newInstance(logger, tokenService, putObjectService, 0, null))
                Assertions.assertNotNull(it.newInstance(logger, tokenService, putObjectService, 1, null))
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


        val command = GreetingCommand(logger, tokenService, putObjectService)
        command.run()

        Assertions.assertEquals("token is you", logMessages[0])
        verify { putObjectService.put(any()) }
        Assertions.assertEquals("~~~~~~~~~~~~~~~~~~logging~~~~~~~~~~~~", logMessages[1])
    }
}