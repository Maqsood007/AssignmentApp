package com.task.shortlyapp.repository.models

data class ShortenUrlErrorResponse(
    val error: String?,
    val error_code: Int,
    val ok: Boolean
)
