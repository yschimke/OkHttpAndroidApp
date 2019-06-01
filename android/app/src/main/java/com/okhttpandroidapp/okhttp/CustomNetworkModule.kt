package com.okhttpandroidapp.okhttp

import com.facebook.react.modules.network.OkHttpClientFactory
import com.facebook.react.modules.network.ReactCookieJarContainer
import com.okhttpandroidapp.networks.NetworksLiveData
import okhttp3.ConnectionPool

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal class CustomNetworkModule : OkHttpClientFactory {
    val connectionPool = ConnectionPool()
    val cookieJarContainer = ReactCookieJarContainer()
    val client = OkHttpClient.Builder()
            .connectionPool(connectionPool)
            .callTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
//            .pingInterval(3, TimeUnit.SECONDS)
            .cookieJar(cookieJarContainer)
            .build()

    override fun createNewNetworkModuleClient(): OkHttpClient {
        return client
    }
}