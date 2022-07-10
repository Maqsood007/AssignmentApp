package com.task.shortlyapp.utils

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate

object ThemeUtils {
    fun Activity.disableDarkTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}
