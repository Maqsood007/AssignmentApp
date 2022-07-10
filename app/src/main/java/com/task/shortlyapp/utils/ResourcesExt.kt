package com.task.shortlyapp.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

fun getDrawable(context: Context?, drawableId: Int): Drawable? {
    context?.let {
        return ContextCompat.getDrawable(it, drawableId)
    }
    return null
}

fun getColor(context: Context?, colorId: Int): Int? {
    context?.let {
        return ContextCompat.getColor(it, colorId)
    }
    return null
}
