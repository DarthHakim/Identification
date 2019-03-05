package com.engineeringidea.identification.identification

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.engineeringidea.identification.BuildConfig
import com.engineeringidea.identification.identification.IdentificationConst.ID_TOKEN_NULL
import com.engineeringidea.identification.identification.IdentificationConst.LOG_ENABLED
import com.engineeringidea.identification.identification.IdentificationConst.OTHER_ERROR
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.twitter.sdk.android.core.identity.TwitterLoginButton

class IdentificationApp(activity: Activity) {

    private val auth = FirebaseAuth.getInstance()
    private var google: IdentificationGoogle
    private var twitter: IdentificationTwitter
    private val callback: IdentificationCallback

    init {
        google = IdentificationGoogle(activity, auth, this)
        twitter = IdentificationTwitter(activity, auth, this)

        if (activity is IdentificationCallback) {
            callback = activity
        } else {
            throw ClassCastException(
                "Not found IdentificationCallback. " +
                        "Please implement in your activity IdentificationCallback."
            )
        }
    }

    fun addSignGoogleButton(button: SignInButton) {
        google.initUI(button)
    }

    fun addSignTwitterButton(twitterButton: TwitterLoginButton) {
        twitter.initUI(twitterButton)
    }

    fun checkCurrentUser() {
        val currentUser = auth.currentUser
        onUpdateUI(currentUser)
    }

    fun onUpdateUI(currentUser: FirebaseUser?) {
        if (currentUser == null) {
            callback.onIdentificationUserNotFound()
            logInfo("user not found")
        } else {
            currentUser.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val idToken = task.result!!.token
                        if (idToken == null) {
                            onError(ID_TOKEN_NULL, NullPointerException("id token is null"))
                            logError("token is null")
                        } else {
                            callback.onIdentificationUserFound(currentUser, idToken)
                            logInfo("token received successfully, token: $idToken")
                        }
                    } else {
                        val exception = task.exception
                        if (exception == null) {
                            onError(OTHER_ERROR, Exception("Unknown error"))
                            logError("Unknown error")
                        } else {
                            onError(OTHER_ERROR, exception)
                            logError("Unknown error", exception)
                        }
                    }
                }
        }
    }

    fun onError(errorCode: Int, error: Exception) {
        callback.onIdentificationError(errorCode, error)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            IdentificationConst.GOOGLE_RC_SIGN_IN -> google.onActivityResult(data)
            IdentificationConst.TWITTER_RC_SIGN_IN -> twitter.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun logInfo(msg: String) {
        if (BuildConfig.DEBUG && LOG_ENABLED) {
            Log.i(IdentificationConst.TAG, msg)
        }
    }

    fun logError(msg: String) {
        logError(msg, null)
    }

    fun logError(msg: String, e: Exception?) {
        if (BuildConfig.DEBUG && LOG_ENABLED) {
            if (e == null) {
                Log.e(IdentificationConst.TAG, msg)
            } else {
                Log.e(IdentificationConst.TAG, msg, e)
            }
        }
    }
}