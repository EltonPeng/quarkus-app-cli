package com.zijian

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zijian.utils.LocalDateAdapter
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ConvertService {

    fun goWithMoshi(token: Token): String {
        val moshi = Moshi.Builder().add(LocalDateAdapter()).addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(Token::class.java)

        return jsonAdapter.toJson(token)
    }

    fun moshiToObject(json: String): Token? {
        val moshi = Moshi.Builder().add(LocalDateAdapter()).addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(Token::class.java)

        return jsonAdapter.fromJson(json)
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun goListWithMoshi(tokens: List<Token>): String {
        tokens.forEach {
            val originalToken = it.accessToken
            it.accessToken = originalToken.padEnd(it.finalLength, '0')
            it.accessTokenUpper = originalToken.padEnd(it.finalLength, '9')
        }
        val moshi = Moshi.Builder().add(LocalDateAdapter()).addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter<List<Token>>()

        val sortedList = tokens.sortedWith(Comparator.comparingInt<Token> { -it.accessToken.length }
            .then { o1, o2 ->
                if (o1.accessToken == o2.accessToken) {
                    o2.accessTokenUpper.toInt().compareTo(o1.accessTokenUpper.toInt())
                } else {
                    o1.accessToken.toInt().compareTo(o2.accessToken.toInt())
                }
            })


        return jsonAdapter.toJson(sortedList)
    }

    fun goWithJackson(token: Token): String {
        val mapper = jacksonObjectMapper()
        mapper.registerModule(JavaTimeModule())
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        return mapper.writeValueAsString(token)
    }

    fun jacksonToObject(json: String): Token {
        val mapper = jacksonObjectMapper()
        mapper.registerModule(JavaTimeModule())
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        return mapper.readValue(json, Token::class.java)
    }
}
