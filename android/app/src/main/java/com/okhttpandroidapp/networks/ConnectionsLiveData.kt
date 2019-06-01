package com.okhttpandroidapp.networks

import android.Manifest
import android.arch.lifecycle.LiveData
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.annotation.RequiresPermission
import okhttp3.ConnectionPool
import okhttp3.Route
import okhttp3.internal.connection.RealConnection
import java.net.Socket
import java.util.*
import kotlin.concurrent.timer
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

@RequiresApi(Build.VERSION_CODES.M)
class ConnectionsLiveData
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
constructor(val connectionPool: ConnectionPool)
    : LiveData<ConnectionPoolState>() {

    private lateinit var activeTimer: Timer
    private var lastState: ConnectionPoolState? = null
    private val connectionMap = mutableMapOf<Int, String>()
    private var connectionId = 0

    init {
        update()
    }

    private fun update() {
        val connections = listConnections()
        val newState = ConnectionPoolState(connectionPool.connectionCount(),
                connectionPool.idleConnectionCount(), connections)

        if (newState != lastState) {
            postValue(newState)
            lastState = newState
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun listConnections(): List<ConnectionState> {
        val field = ConnectionPool::class.memberProperties.find { it.name == "connections" }!!
        field.isAccessible = true

        val connections: Collection<RealConnection> = field.get(connectionPool) as Collection<RealConnection>

        return connections.map { toConnectionState(it) }
    }

    private fun toConnectionState(it: RealConnection): ConnectionState {
        val r = it.route()
        val s = it.socket()
        return ConnectionState(id(s), remoteIp(r), r.socketAddress().port,
                r.proxy().toString(), r.address().url().host(), s.localAddress.hostAddress)
    }

    private fun id(s: Socket): String {
        return connectionMap.computeIfAbsent(System.identityHashCode(s)) {
            "" + ++connectionId
        }
    }

    private fun remoteIp(r: Route): String {
        val socketAddress = r.socketAddress().address
        return socketAddress.hostAddress
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActive() {
        activeTimer = timer(period = 1000) {
            update()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onInactive() {
        activeTimer.cancel()
    }
}