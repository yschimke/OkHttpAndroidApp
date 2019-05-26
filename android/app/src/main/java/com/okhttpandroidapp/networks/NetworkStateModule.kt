package com.okhttpandroidapp.networks

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.util.Log
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.okhttpandroidapp.MainActivity

class NetworkStateModule(reactContext: ReactApplicationContext,
                         val networksLiveData: NetworksLiveData,
                         val connectionsLiveData: ConnectionsLiveData) : ReactContextBaseJavaModule(reactContext) {
    @ReactMethod
    fun getNetworks(promise: Promise) {
        val current = networksLiveData.value

        if (current != null) {
            promise.resolve(readNetworks(current))
        } else {
            val observer = object : Observer<NetworksState> {
                override fun onChanged(it: NetworksState?) {
                    promise.resolve(it?.toMap())
                    networksLiveData.removeObserver(this)
                }
            }
            networksLiveData.observe(this.currentActivity as MainActivity, observer)
        }
    }

    fun startListeners(lifecycleOwner: LifecycleOwner) {
        val observer = Observer<NetworksState> {
            if (it != null) {
                publishEvent(reactApplicationContext, it)
            }
        }
        networksLiveData.observe(lifecycleOwner, observer)

        val observer2 = Observer<ConnectionPoolState> {
            if (it != null) {
                publishEvent(reactApplicationContext, it)
            }
        }
        connectionsLiveData.observe(lifecycleOwner, observer2)
    }

    @ReactMethod
    fun getConnections(promise: Promise) {
        val current = connectionsLiveData.value

        if (current != null) {
            promise.resolve(current.toMap())
        } else {
            val observer = object : Observer<ConnectionPoolState> {
                override fun onChanged(it: ConnectionPoolState?) {
                    promise.resolve(it?.toMap())
                    connectionsLiveData.removeObserver(this)
                }
            }
            connectionsLiveData.observe(this.currentActivity as MainActivity, observer)
        }
    }

    private fun publishEvent(reactContext: ReactApplicationContext, state: NetworksState) {
        Log.w("NetworkStateModule", "${state.networks.map { it.name }} ${state.events.lastOrNull()?.event}")
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit("networkStateChanged", state.toMap())
    }

    private fun publishEvent(reactContext: ReactApplicationContext, state: ConnectionPoolState) {
        Log.w("NetworkStateModule", "$state")
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit("connectionPoolStateChanged", state.toMap())
    }

    private fun readNetworks(current: NetworksState): WritableMap {
        Log.w("NetworkState", "" + current)
        return current.toMap()
    }

    override fun getName(): String {
        return "NetworkState"
    }
}