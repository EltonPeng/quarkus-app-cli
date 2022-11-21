package com.zijian

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

internal class ConvertServiceTest {

    @Test
    fun goWithMoshi() {
        val convertService = ConvertService()
        val token = Token("a", LocalDate.EPOCH, TokenType.Long)
        val output = convertService.goWithMoshi(token)

        assertEquals("""{"accessToken":"a","expiredIn":"1970-01-01","tokenType":"Long"}""", output)
    }
}