package com.zijian

import io.mockk.*
import io.mockk.impl.annotations.MockK
import mu.KLogger
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

internal class GreetingCommandTest {

    @MockK
    private lateinit var logger: KLogger

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun run() {
        every { logger.info(any<String>()) } just Runs

        val command = GreetingCommand(logger)
        command.run()

        verify { logger.info ( "~~~~~~~~~~~~~~~~~~logging~~~~~~~~~~~~" ) }
    }
}