package com.okhttpandroidapp.networks

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

data class NetworkState(val networkId: String?, val name: String, val type: String,
                        val connected: Boolean, val state: String, val downstreamKbps: Int,
                        val upstreamKbps: Int) {
    fun toMap(): WritableMap {
        return Arguments.createMap().apply {
            putString("networkId", networkId)
            putString("name", name)
            putString("type", type)
            putBoolean("connected", connected)
            putString("state", state)
            putInt("downstreamKbps", downstreamKbps)
            putInt("upstreamKbps", upstreamKbps)
        }
    }
}
