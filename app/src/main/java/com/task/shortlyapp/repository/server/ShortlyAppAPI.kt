package com.task.shortlyapp.repository.server

import com.task.shortlyapp.repository.models.ShortenUrlResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ShortlyAppAPI {

    @GET("shorten?url={url}")
    suspend fun shortenUrl(@Path("url") url: String): ShortenUrlResponse
}
