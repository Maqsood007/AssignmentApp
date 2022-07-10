package com.task.shortlyapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.task.shortlyapp.R
import com.task.shortlyapp.utils.ThemeUtils.disableDarkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        disableDarkTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
