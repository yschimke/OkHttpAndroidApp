package com.okhttpandroidapp.networks

import android.app.Application
import android.arch.lifecycle.LiveData
import android.content.Context
import android.net.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.annotation.RequiresPermission
import android.util.Log
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.concurrent.CopyOnWriteArrayList

@RequiresApi(Build.VERSION_CODES.M)
class NetworksLiveData
@RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
constructor(application: Application)
    : LiveData<NetworksState>() {
    private var events = CopyOnWriteArrayList(listOf(NetworkEvent(null, "App Started")))
    private val connectivityManager: ConnectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            show(NetworkEvent(network.toString(), "available"))
        }

        override fun onUnavailable() {
            show(NetworkEvent(null, "unavailable"))
        }

        override fun onLost(network: Network) {
            show(NetworkEvent(network.toString(), "lost"))
        }

        override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
            // TODO describe properties
            show(NetworkEvent(network.toString(), "properties changed"))
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            show(NetworkEvent(network.toString(), "losing in $maxMsToLive ms"))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun show(networkEvent: NetworkEvent) {
        this.events.add(networkEvent)
        postValue(networksState())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun networksState(): NetworksState {
        val activeNetwork = connectivityManager.activeNetwork?.toString()
        val networks = connectivityManager.allNetworks.map { describe(it, activeNetwork == it.toString()) }
        val networksState = NetworksState(networks, events.toList(), activeNetwork)

        Log.w("NetworksLiveData", "" + networksState)

        return networksState
    }

    @Suppress("DEPRECATION")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun describe(network: Network, active: Boolean): NetworkState {
        val info: NetworkInfo? = connectivityManager.getNetworkInfo(network)
        val capabilities: NetworkCapabilities? = connectivityManager.getNetworkCapabilities(network)
        val properties: LinkProperties? = connectivityManager.getLinkProperties(network)

        val name = info?.detailedState?.name
        val localAddress: InetAddress? = when {
            name != null -> NetworkInterface.getByName(name)?.inetAddresses?.toList()?.firstOrNull()
            else -> null
        }

        return NetworkState(network.toString(), properties?.interfaceName
                ?: "unknown", info?.typeName + "/" + info?.subtypeName, info?.isConnected,
                name, capabilities?.linkDownstreamBandwidthKbps,
                capabilities?.linkUpstreamBandwidthKbps, active,
                localAddress?.hostAddress)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActive() {
        val request = NetworkRequest.Builder().addCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onInactive() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}