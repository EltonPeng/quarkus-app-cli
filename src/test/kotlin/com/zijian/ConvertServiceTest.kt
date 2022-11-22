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

    @Test
    fun goListWithMoshi() {
        val convertService = ConvertService()
        val token1 = Token("a", LocalDate.EPOCH, TokenType.Long)
        val token2 = Token("b", LocalDate.EPOCH, TokenType.Short)
        val output = convertService.goListWithMoshi(listOf(token1, token2))

        assertEquals(
            """[{"accessToken":"a","expiredIn":"1970-01-01","tokenType":"Long"},{"accessToken":"b","expiredIn":"1970-01-01","tokenType":"Short"}]""",
            output
        )
    }

    @Test
    fun orderForAsc() {
        val convertService = ConvertService()
        val token1 = Token("2", LocalDate.EPOCH, TokenType.Long)
        val token2 = Token("1", LocalDate.EPOCH, TokenType.Short)
        val output = convertService.goListWithMoshi(listOf(token1, token2))

        assertEquals(
            """[{"accessToken":"1","expiredIn":"1970-01-01","tokenType":"Short"},{"accessToken":"2","expiredIn":"1970-01-01","tokenType":"Long"}]""",
            output
        )
    }

    @Test
    fun goWithJackson() {
        val convertService = ConvertService()
        val token = Token("a", LocalDate.EPOCH, TokenType.Long)
        val output = convertService.goWithJackson(token)

        assertEquals("""{"accessToken":"a","expiredIn":"1970-01-01","tokenType":"Long"}""", output)
    }
}