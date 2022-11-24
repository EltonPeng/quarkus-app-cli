package com.zijian

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zijian.utils.LocalDateAdapter

class ConvertService {

    fun goWithMoshi(token: Token): String {
        val moshi = Moshi.Builder().add(LocalDateAdapter()).addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(Token::class.java)

        return jsonAdapter.toJson(token)
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun goListWithMoshi(tokens: List<Token>): String {
        tokens.forEach { it.accessToken = it.accessToken.padEnd(it.finalLength, '0') }
        val moshi = Moshi.Builder().add(LocalDateAdapter()).addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter<List<Token>>()

        val sortedList = tokens.sortedWith(Comparator.comparingInt<Token> { -it.accessToken.length }
            .then { o1, o2 ->
                if (o1.accessToken == o2.accessToken) {
                    o2.accessToken.toInt().compareTo(o1.accessToken.toInt())
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
}
