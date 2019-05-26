package com.okhttpandroidapp.networks

import android.arch.lifecycle.LiveData
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.annotation.RequiresPermission
import okhttp3.ConnectionPool
import java.util.*
import kotlin.concurrent.timer

@RequiresApi(Build.VERSION_CODES.M)
class ConnectionsLiveData
@RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
constructor(val connectionPool: ConnectionPool)
    : LiveData<ConnectionPoolState>() {

    private lateinit var activeTimer: Timer
    private var lastState: ConnectionPoolState? = null

    init {
        update()
    }

    private fun update() {
        val newState = ConnectionPoolState(connectionPool.connectionCount(),
                connectionPool.idleConnectionCount(), listOf())

        if (newState != lastState) {
            postValue(newState)
            lastState = newState
        }
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