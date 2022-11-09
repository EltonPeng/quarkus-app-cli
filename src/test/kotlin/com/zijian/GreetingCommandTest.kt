package com.zijian

import io.mockk.*
import io.mockk.impl.annotations.MockK
import mu.KLogger
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.reflect.Constructor

internal class GreetingCommandTest {

    @MockK
    private lateinit var logger: KLogger

    @MockK
    private lateinit var putObjectService: PutObjectService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `cover constructors`() {
        val constructor: Constructor<GreetingCommand> = GreetingCommand::class.java.getDeclaredConstructor()
        val command = constructor.newInstance()

        Assertions.assertNotNull(command)
    }

    @Test
    fun run() {
        val slot = slot<() -> Any?>()
        every { logger.info(capture(slot)) } just Runs
        every { putObjectService.put(any()) } just Runs

        val command = GreetingCommand(logger)
        command.putObjectService = putObjectService
        command.run()

        Assertions.assertEquals("~~~~~~~~~~~~~~~~~~logging~~~~~~~~~~~~", slot.captured.invoke())
        verify { putObjectService.put(any()) }
    }
}