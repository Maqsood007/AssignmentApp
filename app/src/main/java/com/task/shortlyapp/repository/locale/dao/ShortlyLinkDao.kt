package com.task.shortlyapp.repository.locale.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.task.shortlyapp.repository.locale.entity.ShortlyLink

@Dao
interface ShortlyLinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShortlyLink(shortlyLink: ShortlyLink)
}
