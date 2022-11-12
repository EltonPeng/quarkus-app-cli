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

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `cover constructors`() {
        val constructors = GreetingCommand::class.java.constructors
        Assertions.assertEquals(2, constructors.count())
        constructors.forEach {
            if(it.parameters.count() == 2){
                Assertions.assertNotNull(it.newInstance(logger, putObjectService))
            }
            else {
                Assertions.assertNotNull(it.newInstance(logger, putObjectService, 0, null))
            }
        }
    }

    @Test
    fun run() {
        val slot = slot<() -> Any?>()
        every { logger.info(capture(slot)) } just Runs
        every { putObjectService.put(any()) } just Runs

        val command = GreetingCommand(logger, putObjectService)
//        command.putObjectService = putObjectService
        command.run()

        Assertions.assertEquals("~~~~~~~~~~~~~~~~~~logging~~~~~~~~~~~~", slot.captured.invoke())
        verify { putObjectService.put(any()) }
    }
}