package com.kotlinntaivebridge

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.facebook.react.bridge.*
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.HintRequest

class HintRequestPickerModule(private val reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {
    val hintPicker: HintRequestPicker
    var VphoneNumber = ""
    private var pickerPromise: Promise? = null
    private val activityEventListener = object : BaseActivityEventListener() {
        override fun onActivityResult(
            activity: Activity?,
            requestCode: Int,
            resultCode: Int,
            data: Intent?
        ) {
            if (data == null) {
                return;
            }

            if (requestCode == 1001) {
                pickerPromise.let { promise ->


                    if (resultCode == Activity.RESULT_OK) {
                        val credential: Credential? = data.getParcelableExtra(Credential.EXTRA_KEY)
                        if (credential == null) {
                            return;
                        }
                        val phoneNumber = credential.getId();
                        //   Toast.makeText(reactContext, "$phoneNumber", Toast.LENGTH_SHORT).show()
                        VphoneNumber = phoneNumber
                        if (promise != null) {
                            promise.resolve(VphoneNumber)
                        }
                    } else {
                    }
                }
                //sendEvent(Constants.PHONE_SELECTED_EVENT, map)
            }
        }
    }

    init {
        hintPicker = HintRequestPicker(reactContext);

        reactContext.addActivityEventListener(activityEventListener)

    }

    override fun getName(): String {
        return "HintRequestPicker"
    }

    // Example method
    // See https://reactnative.dev/docs/native-modules-android

    @ReactMethod
    fun getPhoneNumber(promise: Promise) {
        val activity = currentActivity

        if (activity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist")
            return
        }



        pickerPromise = promise
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val intent: PendingIntent =
            Credentials.getClient(reactContext).getHintPickerIntent(hintRequest)
        try {
            activity.let { ActivityCompat.startIntentSenderForResult(
                it,
                    intent.intentSender,
                    1001,
                    null,
                    0,
                    0,
                    0,
                    Bundle()
                )
            }
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
            pickerPromise?.reject(E_FAILED_TO_SHOW_PICKER, e)
            pickerPromise = null
        }

    }

    @ReactMethod
    fun showToast() {
        Toast.makeText(reactContext, "Hello from Kotlin", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val IMAGE_PICKER_REQUEST = 1
        const val E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST"
        const val E_PICKER_CANCELLED = "E_PICKER_CANCELLED"
        const val E_FAILED_TO_SHOW_PICKER = "E_FAILED_TO_SHOW_PICKER"
        const val E_NO_IMAGE_DATA_FOUND = "E_NO_IMAGE_DATA_FOUND"
    }


}