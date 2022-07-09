package com.task.shortlyapp.repository

import com.task.shortlyapp.repository.models.ShortenUrlResponse
import com.task.shortlyapp.repository.server.ShortlyAppAPI
import javax.inject.Inject

class ShortlyAppRepository @Inject constructor(private val shortlyAppAPI: ShortlyAppAPI) :
    ShortlyAppAPI {

    override suspend fun shortenUrl(url: String): ShortenUrlResponse {
        return shortlyAppAPI.shortenUrl(url)
    }
}
