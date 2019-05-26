package com.okhttpandroidapp.networks

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

data class ConnectionPoolState(val connectionCount: Int, val idleConnectionCount: Int,
                               val connections: List<ConnectionState>) {
    fun toMap(): WritableMap {
        val result = Arguments.createMap()

        val connectionsArray = Arguments.createArray()
        connections.forEach {
            connectionsArray.pushMap(it.toMap())
        }

        result.putInt("connectionsCount", connectionCount)
        result.putInt("idleConnectionsCount", idleConnectionCount)
        result.putArray("connections", connectionsArray)

        return result
    }
}