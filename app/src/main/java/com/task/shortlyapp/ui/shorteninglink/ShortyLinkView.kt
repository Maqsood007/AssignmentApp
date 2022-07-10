package com.task.shortlyapp.ui.shorteninglink

interface ShortyLinkView {
    fun triggerShorty()
    fun onShortyError(message: String?)
    fun emptyURLError()
    fun getEnteredLink(): String
}
