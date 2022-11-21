package com.zijian

import io.mockk.*
import io.mockk.impl.annotations.MockK
import mu.KLogger
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
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

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `cover constructors`() {
        val constructors = GreetingCommand::class.java.constructors
        constructors.forEach {
            if(it.parameters.isEmpty()) {
                assertNotNull(it.newInstance())
            } else if(it.parameters.count() == 4){
                assertNotNull(it.newInstance(logger, tokenService, convertService, putObjectService))
            }
            else {
                assertNotNull(it.newInstance(logger, tokenService, convertService, putObjectService, 0, null))
                assertNotNull(it.newInstance(logger, tokenService, convertService, putObjectService, 1, null))
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
        every { tokenService.get() } returns Token("you", LocalDate.EPOCH, TokenType.Temp)


        val command = GreetingCommand(logger, tokenService, convertService, putObjectService)
        //command.tokenService = tokenService
        command.run()

        assertEquals("token is you", logMessages[0])
        verify { putObjectService.put(any()) }
        assertEquals("~~~~~~~~~~~~~~~~~~logging~~~~~~~~~~~~", logMessages[1])
    }
}