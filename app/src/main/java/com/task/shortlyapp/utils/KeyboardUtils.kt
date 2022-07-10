package com.task.shortlyapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Activity.hideKeyboard() {
    when {
        currentFocus != null -> currentFocus
        getActivityRoot() != null -> getActivityRoot()
        else -> View(this)
    }?.let { focusedView ->
        hideKeyboard(focusedView)
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.getActivityRoot(): View? {
    return (findViewById<ViewGroup>(android.R.id.content)).getChildAt(0)
}
