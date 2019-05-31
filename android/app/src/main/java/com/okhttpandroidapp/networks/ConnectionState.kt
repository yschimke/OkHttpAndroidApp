package com.okhttpandroidapp.networks

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

data class ConnectionState(val id: String, val destHost: String, val destPort: Int,
                           val proxy: String, val host: String, val localAddress: String) {
    fun toMap(): WritableMap {
        return Arguments.createMap().apply {
            putString("id", id)
            putString("destHost", destHost)
            putInt("destPort", destPort)
            putString("proxy", proxy)
            putString("host", host)
            putString("localAddress", localAddress)
        }
    }
}
