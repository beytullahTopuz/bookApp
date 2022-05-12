package com.t4zb.aws.modellayer.network

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {
    @Suppress("DEPRECATION")
    fun isNetworkAvailable(app: Context): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }
}