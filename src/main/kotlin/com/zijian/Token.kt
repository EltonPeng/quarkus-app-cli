package com.zijian

import java.time.LocalDate

class Token(
    var accessToken: String,
    var accessTokenUpper: String = "",
    val expiredIn: LocalDate,
    val tokenType: TokenType,
    val finalLength: Int = 1
)

enum class TokenType {
    Temp,
    Short,
    Long
}