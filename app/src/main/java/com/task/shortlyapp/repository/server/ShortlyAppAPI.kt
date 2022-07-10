package com.task.shortlyapp.repository.server

import com.task.shortlyapp.repository.models.ShortenUrlResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ShortlyAppAPI {

    @GET("shorten")
    suspend fun shortenUrl(@Query("url") url: String): ShortenUrlResponse
}
