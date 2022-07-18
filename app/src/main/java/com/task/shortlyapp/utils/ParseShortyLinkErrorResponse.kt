package com.task.shortlyapp.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.shortlyapp.repository.models.ShortenUrlErrorResponse
import okhttp3.ResponseBody

fun parseShortyLinkErrorResponse(errorBody: ResponseBody?): ShortenUrlErrorResponse? {
    val gson = Gson()
    val type = object : TypeToken<ShortenUrlErrorResponse>() {}.type
    return gson.fromJson(errorBody?.charStream(), type)
}
