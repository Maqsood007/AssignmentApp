package com.task.shortlyapp.repository.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.task.shortlyapp.repository.locale.entity.ShortlyLink

@Dao
interface ShortlyLinkDao {

    @Query("SELECT * FROM shortly_links ORDER BY created_at DESC")
    suspend fun getAllShortlyLinks(): List<ShortlyLink>

    @Query("SELECT count(*) from shortly_links")
    suspend fun getAllShortlyLinksCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShortlyLink(shortlyLink: ShortlyLink)

    @Query("DELETE FROM shortly_links WHERE code = :code")
    suspend fun deleteByCode(code: String)

    @Query("SELECT * FROM shortly_links WHERE original_link = :originalLink")
    suspend fun getShortenLinkByOriginalLink(originalLink: String) : ShortlyLink?
}
