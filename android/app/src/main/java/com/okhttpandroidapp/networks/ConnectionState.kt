package com.okhttpandroidapp.networks

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

data class ConnectionState(val id: String) {
    fun toMap(): WritableMap {
        return Arguments.createMap().apply {
            putString("id", id)
        }
    }
}
