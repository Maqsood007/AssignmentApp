package com.task.shortlyapp.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Activity.showToast(message: String) {
    showToast(this, message)
}

fun Fragment.showToast(message: String) {
    showToast(requireContext(), message)
}
