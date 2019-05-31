package com.okhttpandroidapp.networks

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap

data class NetworksState(val networks: List<NetworkState>, val events: List<NetworkEvent>,
                         val activeNetwork: String?) {
    fun toMap(): WritableMap {
        val result = Arguments.createMap()

        val networksArray = Arguments.createArray()
        networks.forEach {
            networksArray.pushMap(it.toMap())
        }

        val eventsArray = Arguments.createArray()
        events.forEach {
            eventsArray.pushMap(it.toMap())
        }

        result.putArray("networks", networksArray)
        result.putArray("events", eventsArray)
        result.putString("activeNetwork", activeNetwork)

        return result
    }
}
