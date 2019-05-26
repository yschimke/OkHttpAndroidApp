package com.okhttpandroidapp

import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.facebook.react.ReactActivity

class MainActivity : ReactActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        // TODO Yuck
        Handler().postDelayed({
            Log.w("MainActivity", "start network listener")
            val mainApplication = this.application as MainApplication
            mainApplication.networksPackage.stateModule.startListeners(this)
        }, 1000)
    }

    override fun getMainComponentName(): String? {
        return "OkHttpAndroidApp"
    }
}
