package com.task.shortlyapp.repository

import com.task.shortlyapp.repository.locale.entity.ShortlyLink

object DateMockUtils {
    fun getShortlyLink(
        code: String = "YAtuA",
        full_share_link: String? = "https:\\/\\/shrtco.de\\/share\\/YAtuA",
        full_short_link: String? = "https:\\/\\/shrtco.de\\/YAtuA",
        full_short_link2: String? = "https:\\/\\/9qr.de\\/YAtuA",
        original_link: String? = "http:\\/\\/www.facebook.co.com\"",
        share_link: String? = "shrtco.de\\/YAtuA",
        short_link: String? = "shrtco.de\\/YAtuA",
        short_link2: String? = "9qr.de\\/YAtuA",
        created_at: Long = System.currentTimeMillis()
    ): ShortlyLink {
        return ShortlyLink(
            code,
            full_share_link,
            full_short_link,
            full_short_link2,
            original_link,
            share_link,
            short_link,
            short_link2,
            created_at
        )
    }
}