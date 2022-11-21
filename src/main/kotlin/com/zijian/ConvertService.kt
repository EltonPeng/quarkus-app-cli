package com.zijian

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zijian.utils.LocalDateAdapter

class ConvertService {

    fun goWithMoshi(token:Token): String {
        val moshi = Moshi.Builder().add(LocalDateAdapter()).addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(Token::class.java)

        return jsonAdapter.toJson(token)
    }

    fun goWithJackson(token: Token): String {
        val mapper = jacksonObjectMapper()
        mapper.registerModule(JavaTimeModule())
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        return mapper.writeValueAsString(token)
    }
}