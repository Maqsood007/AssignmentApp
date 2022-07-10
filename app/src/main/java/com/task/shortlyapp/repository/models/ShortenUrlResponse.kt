package com.task.shortlyapp.repository.models

import com.google.gson.annotations.SerializedName
import com.task.shortlyapp.repository.locale.entity.ShortlyLink

data class ShortenUrlResponse(
    val ok: Boolean,
    @SerializedName("result")
    val result: ShortlyLink
)
