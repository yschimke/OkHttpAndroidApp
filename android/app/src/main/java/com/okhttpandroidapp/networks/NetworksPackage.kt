package com.okhttpandroidapp.networks

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

class NetworksPackage(val networksLiveData: NetworksLiveData,
                      val connectionsLiveData: ConnectionsLiveData) : ReactPackage {
    internal lateinit var stateModule: NetworkStateModule

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList()
    }

    override fun createNativeModules(
            reactContext: ReactApplicationContext): List<NativeModule> {
        stateModule = NetworkStateModule(reactContext, networksLiveData, connectionsLiveData)
        return listOf(stateModule)
    }
}