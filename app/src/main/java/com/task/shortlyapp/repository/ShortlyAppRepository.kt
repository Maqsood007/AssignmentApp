package com.task.shortlyapp.repository

import com.task.shortlyapp.repository.locale.ShortlyDatabase
import com.task.shortlyapp.repository.locale.dao.ShortlyLinkDao
import com.task.shortlyapp.repository.locale.entity.ShortlyLink
import com.task.shortlyapp.repository.models.ShortenUrlResponse
import com.task.shortlyapp.repository.server.ShortlyAppAPI
import javax.inject.Inject

class ShortlyAppRepository @Inject constructor(
    private val shortlyAppAPI: ShortlyAppAPI,
    private val shortlyDatabase: ShortlyDatabase
) :
    ShortlyAppAPI, ShortlyLinkDao {

    override suspend fun shortenUrl(url: String): ShortenUrlResponse {
        return shortlyAppAPI.shortenUrl(url)
    }

    override suspend fun addShortlyLink(shortlyLink: ShortlyLink) {
        shortlyDatabase.shortlyLinkDao().addShortlyLink(shortlyLink)
    }
}
