package com.zijian

import io.mockk.*
import io.mockk.impl.annotations.MockK
import mu.KLogger
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

internal class GreetingCommandTest {

    @MockK
    private lateinit var logger: KLogger

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun run() {
        val slot = slot<() -> Any?>()
        every { logger.info(capture(slot)) } just Runs

        val command = GreetingCommand(logger)
        command.run()

        Assertions.assertEquals("~~~~~~~~~~~~~~~~~~logging~~~~~~~~~~~~", slot.captured.invoke())
    }
}