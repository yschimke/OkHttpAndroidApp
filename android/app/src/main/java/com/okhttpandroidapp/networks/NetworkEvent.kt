package com.okhttpandroidapp.networks

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import java.util.*

data class NetworkEvent(val networkId: String?, val event: String, val id: String = UUID.randomUUID().toString()) {
    fun toMap(): WritableMap {
        return Arguments.createMap().apply {
            putString("id", id)
            putString("networkId", networkId)
            putString("event", event)
        }
    }
}
