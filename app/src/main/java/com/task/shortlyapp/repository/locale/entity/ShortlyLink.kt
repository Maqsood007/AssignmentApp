package com.task.shortlyapp.repository.locale.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "shortly_links")
data class ShortlyLink(
    @PrimaryKey
    val code: String,
    val full_share_link: String?,
    val full_short_link: String?,
    val full_short_link2: String?,
    val original_link: String?,
    val share_link: String?,
    val short_link: String?,
    val short_link2: String?,
    var created_at: Long
) {
    @Ignore
    var copied: Boolean = false

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is ShortlyLink && other.code == code
    }
}
