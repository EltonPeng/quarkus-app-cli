package com.zijian

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

internal class ConvertServiceTest {

    @Test
    fun goWithMoshi() {
        val convertService = ConvertService()
        val token = Token("1", LocalDate.EPOCH, TokenType.Long)
        val output = convertService.goWithMoshi(token)

        assertEquals("""{"accessToken":"1","expiredIn":"1970-01-01","tokenType":"Long","finalLength":1}""", output)
    }

    @Test
    fun goListWithMoshi() {
        val convertService = ConvertService()
        val token1 = Token("1", LocalDate.EPOCH, TokenType.Long)
        val token2 = Token("2", LocalDate.EPOCH, TokenType.Short)
        val output = convertService.goListWithMoshi(listOf(token1, token2))

        assertEquals(
            """[{"accessToken":"1","expiredIn":"1970-01-01","tokenType":"Long","finalLength":1},{"accessToken":"2","expiredIn":"1970-01-01","tokenType":"Short","finalLength":1}]""",
            output
        )
    }

    @Test
    fun orderForTokenLengthDesc() {
        val convertService = ConvertService()
        val token1 = Token("20", LocalDate.EPOCH, TokenType.Long, 3)
        val token2 = Token("1", LocalDate.EPOCH, TokenType.Short)
        val output = convertService.goListWithMoshi(listOf(token2, token1))

        assertEquals(
            """[{"accessToken":"200","expiredIn":"1970-01-01","tokenType":"Long","finalLength":3},{"accessToken":"1","expiredIn":"1970-01-01","tokenType":"Short","finalLength":1}]""",
            output
        )
    }

    @Test
    fun orderForTokenValueAsc() {
        val convertService = ConvertService()
        val token1 = Token("2", LocalDate.EPOCH, TokenType.Long)
        val token2 = Token("1", LocalDate.EPOCH, TokenType.Short)
        val output = convertService.goListWithMoshi(listOf(token1, token2))

        assertEquals(
            """[{"accessToken":"1","expiredIn":"1970-01-01","tokenType":"Short","finalLength":1},{"accessToken":"2","expiredIn":"1970-01-01","tokenType":"Long","finalLength":1}]""",
            output
        )
    }

    @Test
    fun goWithJackson() {
        val convertService = ConvertService()
        val token = Token("1", LocalDate.EPOCH, TokenType.Long)
        val output = convertService.goWithJackson(token)

        assertEquals("""{"accessToken":"1","expiredIn":"1970-01-01","tokenType":"Long","finalLength":1}""", output)
    }
}