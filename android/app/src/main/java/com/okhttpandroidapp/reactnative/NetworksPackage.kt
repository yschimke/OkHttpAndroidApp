package com.okhttpandroidapp.reactnative

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import com.okhttpandroidapp.MainActivity
import ee.schimke.okhttp.android.android.PhoneStatusLiveData
import ee.schimke.okhttp.android.networks.ConnectionsLiveData
import ee.schimke.okhttp.android.networks.NetworksLiveData
import ee.schimke.okhttp.android.networks.RequestsLiveData

class NetworksPackage(val networksLiveData: NetworksLiveData,
                      val connectionsLiveData: ConnectionsLiveData,
                      val requestsLiveData: RequestsLiveData,
                      val phoneStatusLiveData: PhoneStatusLiveData) : ReactPackage {
    private var activity: MainActivity? = null
    internal lateinit var stateModule: NetworkStateModule

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList()
    }

    override fun createNativeModules(
            reactContext: ReactApplicationContext): List<NativeModule> {
        stateModule = NetworkStateModule(reactContext,
                connectionsLiveData,
                networksLiveData,
                phoneStatusLiveData,
                requestsLiveData
        )
        if (activity != null) {
            stateModule.startListeners(activity!!)
        }
        return listOf(stateModule)
    }

    fun startListeners(mainActivity: MainActivity) {
        // TODO unravel this catastrophe
        if (this::stateModule.isInitialized) {
            stateModule.startListeners(mainActivity)
        } else {
            activity = mainActivity
        }
    }
}