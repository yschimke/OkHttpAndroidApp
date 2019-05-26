package com.okhttpandroidapp.okhttp

import com.facebook.react.modules.network.OkHttpClientFactory
import com.facebook.react.modules.network.ReactCookieJarContainer
import com.okhttpandroidapp.networks.NetworksLiveData
import okhttp3.ConnectionPool

import okhttp3.OkHttpClient

internal class CustomNetworkModule : OkHttpClientFactory {
    val connectionPool = ConnectionPool()
    val cookieJarContainer = ReactCookieJarContainer()
    val client = OkHttpClient.Builder()
            .connectionPool(connectionPool)
            .cookieJar(cookieJarContainer)
            .build()

    override fun createNewNetworkModuleClient(): OkHttpClient {
        return client
    }
}