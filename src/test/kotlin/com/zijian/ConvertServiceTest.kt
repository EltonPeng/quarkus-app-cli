package com.zijian

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.squareup.moshi.JsonEncodingException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.io.EOFException
import java.time.LocalDate

internal class ConvertServiceTest {

    @Test
    fun goWithMoshi() {
        val convertService = ConvertService()
        val token = Token("1", "", LocalDate.EPOCH, TokenType.Long)
        val output = convertService.goWithMoshi(token)

        assertEquals("""{"accessToken":"1","accessTokenUpper":"","expiredIn":"1970-01-01","tokenType":"Long","finalLength":1}""", output)
    }

    @Test
    fun `moshi throw EOFException for empty string`() {
        val convertService = ConvertService()
        val json = ""

        assertThrows(EOFException::class.java) { convertService.moshiToObject(json) }
    }

    @Test
    fun `moshi return null for string 'null'`() {
        val convertService = ConvertService()
        val json = "null"

        assertNull(convertService.moshiToObject(json))
    }

    @Test
    fun `moshi throw EOFException for string 'a'`() {
        val convertService = ConvertService()
        val json = "a"

        assertThrows(JsonEncodingException::class.java) { convertService.moshiToObject(json) }
    }

    @Test
    fun `moshi throw EOFException for invalid string`() {
        val convertService = ConvertService()
        val json = """{"accessToken":"1""""

        assertThrows(EOFException::class.java) { convertService.moshiToObject(json) }
    }

    @Test
    fun goListWithMoshi() {
        val convertService = ConvertService()
        val token1 = Token("1", "", LocalDate.EPOCH, TokenType.Long)
        val token2 = Token("2", "", LocalDate.EPOCH, TokenType.Short)
        val output = convertService.goListWithMoshi(listOf(token1, token2))

        assertEquals(
            """[{"accessToken":"1","accessTokenUpper":"1","expiredIn":"1970-01-01","tokenType":"Long","finalLength":1},{"accessToken":"2","accessTokenUpper":"2","expiredIn":"1970-01-01","tokenType":"Short","finalLength":1}]""",
            output
        )
    }

    @Test
    fun orderForTokenLengthDesc() {
        val convertService = ConvertService()
        val token1 = Token("20", "", LocalDate.EPOCH, TokenType.Long, 3)
        val token2 = Token("1", "", LocalDate.EPOCH, TokenType.Short)
        val output = convertService.goListWithMoshi(listOf(token2, token1))

        val longerTokenIndex = output.indexOf(""""accessToken":"200"""")
        val shorterTokenIndex = output.indexOf(""""accessToken":"1"""")
        assertTrue(longerTokenIndex < shorterTokenIndex)
    }

    @Test
    fun orderForTokenValueAsc() {
        val convertService = ConvertService()
        val token1 = Token("2", "", LocalDate.EPOCH, TokenType.Long)
        val token2 = Token("1", "", LocalDate.EPOCH, TokenType.Short)
        val output = convertService.goListWithMoshi(listOf(token1, token2))

        val largerTokenIndex = output.indexOf(""""accessToken":"2"""")
        val smallerTokenIndex = output.indexOf(""""accessToken":"1"""")
        assertTrue(smallerTokenIndex < largerTokenIndex)
    }

    @Test
    fun orderForTokenRangeDesc() {
        val convertService = ConvertService()
        val token1 = Token("20", "", LocalDate.EPOCH, TokenType.Long, 3)
        val token2 = Token("2", "", LocalDate.EPOCH, TokenType.Long, 3)
        val output = convertService.goListWithMoshi(listOf(token1, token2))

        val narrowTokenIndex = output.indexOf(""""accessTokenUpper":"209"""")
        val wideTokenIndex = output.indexOf(""""accessTokenUpper":"299"""")
        assertTrue(wideTokenIndex < narrowTokenIndex)
    }

    @Test
    fun orderForTokenLengthDescAndTokenValueAsc() {
        val convertService = ConvertService()
        val token1 = Token("20", "", LocalDate.EPOCH, TokenType.Long, 3)
        val token2 = Token("1", "", LocalDate.EPOCH, TokenType.Long)
        val token3 = Token("2", "", LocalDate.EPOCH, TokenType.Long)
        val output = convertService.goListWithMoshi(listOf(token3, token2, token1))

        val longerTokenIndex = output.indexOf(""""accessToken":"200"""")
        val smallerTokenIndex = output.indexOf(""""accessToken":"1"""")
        val largerTokenIndex = output.indexOf(""""accessToken":"2"""")
        assertTrue(longerTokenIndex < smallerTokenIndex)
        assertTrue(smallerTokenIndex < largerTokenIndex)
    }

    @Test
    fun goWithJackson() {
        val convertService = ConvertService()
        val token = Token("1", "", LocalDate.EPOCH, TokenType.Long)
        val output = convertService.goWithJackson(token)

        assertEquals("""{"accessToken":"1","accessTokenUpper":"","expiredIn":"1970-01-01","tokenType":"Long","finalLength":1}""", output)
    }

    @Test
    fun `jackson throw MismatchedInputException for empty string`() {
        val convertService = ConvertService()
        val json = ""

        assertThrows(MismatchedInputException::class.java) { convertService.jacksonToObject(json) }
    }

    @Test
    fun `jackson throw NullPointerException for string 'null'`() {
        val convertService = ConvertService()
        val json = "null"

        assertThrows(NullPointerException::class.java) { convertService.jacksonToObject(json) }
    }

    @Test
    fun `jackson throw JsonParseException for string 'a'`() {
        val convertService = ConvertService()
        val json = "a"

        assertThrows(JsonParseException::class.java) { convertService.jacksonToObject(json) }
    }

    @Test
    fun `jackson throw EOFException for invalid string`() {
        val convertService = ConvertService()
        val json = """{"accessToken":"1""""

        assertThrows(EOFException::class.java) { convertService.moshiToObject(json) }
    }
}