package com.task.shortlyapp.repository.locale

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.task.shortlyapp.repository.locale.DBConstants.DB_VERSION
import com.task.shortlyapp.repository.locale.DBConstants.DBNAME
import com.task.shortlyapp.repository.locale.dao.ShortlyLinkDao
import com.task.shortlyapp.repository.locale.entity.ShortlyLink

@Database(
    entities = [ShortlyLink::class],
    version = DB_VERSION
)
abstract class ShortlyDatabase : RoomDatabase() {

    //region ABSTRACT

    abstract fun shortlyLinkDao(): ShortlyLinkDao

    //endregion

    //region VARIABLE

    companion object {

        @Volatile
        lateinit var INSTANCE: ShortlyDatabase

        fun getInstance(context: Context): ShortlyDatabase {
            if (this::INSTANCE.isInitialized.not()) {
                synchronized(ShortlyDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ShortlyDatabase::class.java,
                        DBNAME
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    // endregion
}