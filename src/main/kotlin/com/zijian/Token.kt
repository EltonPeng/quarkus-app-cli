package com.zijian

import java.time.LocalDate

class Token(
    val accessToken: String,
    val expiredIn: LocalDate,
    val tokenType: TokenType
)

enum class TokenType {
    Temp,
    Short,
    Long
}