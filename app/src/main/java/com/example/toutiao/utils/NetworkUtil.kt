package com.example.toutiao.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtil {
    companion object {
        fun isNetworkConnected(context: Context): Boolean {
            val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var isWifiConn: Boolean = false
            var isMobileConn: Boolean = false
            connMgr.allNetworks.forEach { network ->
                connMgr.getNetworkInfo(network).apply {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        isWifiConn = isWifiConn or isConnected
                    }
                    if (type == ConnectivityManager.TYPE_MOBILE) {
                        isMobileConn = isMobileConn or isConnected
                    }
                }
            }
            return isWifiConn or isMobileConn
        }
    }
}