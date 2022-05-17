package com.kotlinntaivebridge

import android.widget.Toast
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class ToastModule internal constructor(context: ReactContext): ReactContextBaseJavaModule() {

    val reactContext: ReactContext = context


    override fun getName(): String {
        return "ToastModule"
    }
    
    @ReactMethod
    fun showToast(){
        Toast.makeText(reactContext, "Hello from Kotlin", Toast.LENGTH_SHORT).show()
    }



}