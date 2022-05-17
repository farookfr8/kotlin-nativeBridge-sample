package com.kotlinntaivebridge

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import com.facebook.react.bridge.ActivityEventListener
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.HintRequest


/*class HintRequestPicker(reactContext: ReactApplicationContext) {
    companion object {
        var CREDENTIAL_PICKER_REQUEST = 1
    }

    var phoneNumber = ""

    *//*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == RESULT_OK) {

            // get data from the dialog which is of type Credential
            val credential: com.google.android.gms.auth.api.credentials.Credential? =
                data?.getParcelableExtra(com.google.android.gms.auth.api.credentials.Credential.EXTRA_KEY)

            // set the received data t the text view
            credential?.apply {
                Log.e("OldPhoneSelection", "Success")
                if (credential.id.startsWith("+91")) {

                    // et_mobileNumber.setText(credential.id.substring(3))
                    // et_mobileNumber.setText(credential.id)
                    phoneNumber = credential.id.substring(3)
                } else {
                    // et_mobileNumber.setText(credential.id)
                    phoneNumber = credential.id
                }
            }
        } else if (requestCode == CREDENTIAL_PICKER_REQUEST && resultCode == CredentialsApi.ACTIVITY_RESULT_NO_HINTS_AVAILABLE) {
            Toast.makeText(this, "No phone numbers found", Toast.LENGTH_LONG).show();
            // newPhoneSelection()
        }
    }*//*


    fun getPhoneNumber() {

        // To retrieve the Phone Number hints, first, configure
        // the hint selector dialog by creating a HintRequest object.
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()

        val options = CredentialsOptions.Builder()
            .forceEnableSaveDialog()
            .build()

        // Then, pass the HintRequest object to
        // credentialsClient.getHintPickerIntent()
        // to get an intent to prompt the user to
        // choose a phone number.
        val credentialsClient = com.google.android.gms.auth.api.credentials.Credentials.getClient(
            applicationContext,
            options
        )
        val intent = credentialsClient.getHintPickerIntent(hintRequest)
        try {
            reactContext.addActivityEventListener(mActivityResultListener);



        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
        }

        //promise.resolve(phoneNumber)


    }
    *//*    val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val intent: PendingIntent =
            Credentials.getClient(appContext.baseContext).getHintPickerIntent(hintRequest)
        try {
            appContext.applicationContext?.let {
                startIntentSenderForResult(
                    it as Activity,
                    intent.intentSender,
                    100,
                    null,
                    0,
                    0,
                    0,
                    Bundle()
                )
            }
        } catch (e: IntentSender.SendIntentException) {
            e.printStackTrace()
        }
    }*//*
}*/

class HintRequestPicker(private val appContext: ReactApplicationContext) : ActivityEventListener {
    init {
        appContext.addActivityEventListener(this);
    }

    fun getPhoneNumber() {
        val hintRequest = HintRequest.Builder()
            .setPhoneNumberIdentifierSupported(true)
            .build()
        val intent: PendingIntent =
            Credentials.getClient(appContext.baseContext).getHintPickerIntent(hintRequest)
        try {
            appContext.currentActivity?.let {
                startIntentSenderForResult(
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
        }
    }

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
            val map = Arguments.createMap()
            if (resultCode == Activity.RESULT_OK) {
                val credential: Credential? = data.getParcelableExtra(Credential.EXTRA_KEY)
                if (credential == null) {
                    return;
                }
                val phoneNumber = credential.getId();
                Toast.makeText(appContext, "$phoneNumber", Toast.LENGTH_SHORT).show()
                map.putString("phoneNumber", phoneNumber);
            } else {
                map.putString("phoneNumber", null);
            }
            //sendEvent(Constants.PHONE_SELECTED_EVENT, map)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        //TODO("Not yet implemented")
    }
}
