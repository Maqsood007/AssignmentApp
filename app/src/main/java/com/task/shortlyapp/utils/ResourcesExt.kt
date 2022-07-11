package com.task.shortlyapp.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

fun getDrawable(context: Context, drawableId: Int): Drawable? {
    return ContextCompat.getDrawable(context, drawableId)
}

fun getColor(context: Context, colorId: Int): Int {
    return ContextCompat.getColor(context, colorId)
}
