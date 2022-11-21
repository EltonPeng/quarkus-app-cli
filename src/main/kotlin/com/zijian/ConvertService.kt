package com.zijian

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zijian.utils.LocalDateAdapter

class ConvertService {

    fun goWithMoshi(token:Token): String {
        val moshi = Moshi.Builder().add(LocalDateAdapter()).addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(Token::class.java)

        return jsonAdapter.toJson(token)
    }
}