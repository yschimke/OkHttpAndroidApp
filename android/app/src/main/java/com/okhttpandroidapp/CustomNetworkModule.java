package com.okhttpandroidapp;

import com.facebook.react.modules.network.OkHttpClientFactory;

import okhttp3.OkHttpClient;

class CustomNetworkModule implements OkHttpClientFactory {
    public OkHttpClient createNewNetworkModuleClient() {
        return new OkHttpClient.Builder()
                .build();
    }
}