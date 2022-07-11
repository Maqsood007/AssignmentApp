package com.task.shortlyapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkStatusListener {

    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = cm.activeNetwork
            if (activeNetwork != null) {
                val nc = cm.getNetworkCapabilities(activeNetwork)
                // It will check for both wifi and cellular network
                return nc?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false || nc?.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                ) ?: false
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }
    }
}
