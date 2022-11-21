package com.zijian.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate

class LocalDateAdapter {
    @FromJson
    fun fromJson(value:String): LocalDate{
        return LocalDate.parse(value)
    }

    @ToJson
    fun toJson(value: LocalDate): String{
        return value.toString();
    }
}