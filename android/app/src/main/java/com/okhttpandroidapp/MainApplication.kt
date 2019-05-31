package com.okhttpandroidapp

import android.app.Application
import android.os.Build
import android.support.annotation.RequiresApi
import com.facebook.react.ReactApplication
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.modules.network.OkHttpClientProvider
import com.facebook.react.shell.MainReactPackage
import com.facebook.soloader.SoLoader
import com.okhttpandroidapp.networks.ConnectionsLiveData
import com.okhttpandroidapp.networks.NetworksLiveData
import com.okhttpandroidapp.networks.NetworksPackage
import com.okhttpandroidapp.okhttp.CustomNetworkModule
import java.util.*

@Suppress("unused")
class MainApplication : Application(), ReactApplication {
    internal lateinit var connectionLiveData: ConnectionsLiveData
    internal lateinit var networksPackage: NetworksPackage
    internal var customNetworkModule: CustomNetworkModule = CustomNetworkModule()
    internal lateinit var networksLiveData: NetworksLiveData
    private val mReactNativeHost = object : ReactNativeHost(this) {
        override fun getUseDeveloperSupport(): Boolean {
            return BuildConfig.DEBUG
        }

        override fun getPackages(): List<ReactPackage> {
            return Arrays.asList(
                    MainReactPackage(),
                    networksPackage
            )
        }

        override fun getJSMainModuleName(): String {
            return "index"
        }
    }

    override fun getReactNativeHost(): ReactNativeHost {
        return mReactNativeHost
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        networksLiveData = NetworksLiveData(this)
        connectionLiveData = ConnectionsLiveData(customNetworkModule.connectionPool)
        networksPackage = NetworksPackage(networksLiveData, connectionLiveData)

        OkHttpClientProvider.setOkHttpClientFactory(customNetworkModule)

        super.onCreate()
        SoLoader.init(this, /* native exopackage */ false)
    }
}
