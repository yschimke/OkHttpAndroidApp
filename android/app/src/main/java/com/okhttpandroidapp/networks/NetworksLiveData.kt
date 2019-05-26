package com.okhttpandroidapp.networks

import android.app.Application
import android.arch.lifecycle.LiveData
import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.annotation.RequiresPermission
import android.util.Log

@RequiresApi(Build.VERSION_CODES.M)
class NetworksLiveData
@RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
constructor(application: Application)
    : LiveData<NetworksState>() {
    private var events = mutableListOf(NetworkEvent(null, "App Started"))
    private val connectivityManager: ConnectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            show(NetworkEvent(network.toString(), "available $network"))
        }

        override fun onUnavailable() {
            show(NetworkEvent(null, "unavailable"))
        }

        override fun onLost(network: Network) {
            show(NetworkEvent(network.toString(), "lost $network"))
        }

        override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
            // TODO describe properties
            show(NetworkEvent(network.toString(), "properties of $network changed"))
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            show(NetworkEvent(network.toString(), "losing $network in $maxMsToLive ms"))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun show(networkEvent: NetworkEvent) {
        this.events.add(networkEvent)
        postValue(networksState())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun networksState(): NetworksState {
        val networks = connectivityManager.allNetworks.map { describe(it) }
        val networksState = NetworksState(networks, events, connectivityManager.activeNetwork.toString())

        Log.w("NetworksLiveData", "" + networksState)

        return networksState
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun describe(network: Network): NetworkState {
        val info = connectivityManager.getNetworkInfo(network)
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        val properties = connectivityManager.getLinkProperties(network)
        return NetworkState(network.toString(), properties.interfaceName
                ?: "unknown", info.subtypeName, info.isConnected,
                info.detailedState.name, capabilities.linkDownstreamBandwidthKbps,
                capabilities.linkUpstreamBandwidthKbps)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActive() {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onInactive() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}