package com.okhttpandroidapp.toast

import android.widget.Toast

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

import java.util.HashMap

class ToastModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getConstants(): Map<String, Any>? {
        val constants = HashMap<String, Any>()
        constants[DURATION_SHORT_KEY] = Toast.LENGTH_SHORT
        constants[DURATION_LONG_KEY] = Toast.LENGTH_LONG
        return constants
    }

    @ReactMethod
    fun show(message: String, duration: Int) {
        Toast.makeText(reactApplicationContext, message, duration).show()
    }

    override fun getName(): String {
        return "ToastExample"
    }

    companion object {

        private val DURATION_SHORT_KEY = "SHORT"
        private val DURATION_LONG_KEY = "LONG"
    }
}