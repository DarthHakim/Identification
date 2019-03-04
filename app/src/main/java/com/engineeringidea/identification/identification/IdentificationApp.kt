package com.engineeringidea.identification.identification

import android.app.Activity
import android.content.Intent
import com.engineeringidea.identification.identification.IdentificationConst.API_ERROR
import com.engineeringidea.identification.identification.IdentificationConst.ID_TOKEN_NULL
import com.engineeringidea.identification.identification.IdentificationConst.OTHER_ERROR
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class IdentificationApp(activity: Activity) {

    private val auth = FirebaseAuth.getInstance()
    private val google: IdentificationGoogle
    private val twitter: IdentificationTwitter
    private val callback: IdentificationCallback

    init {
        google = IdentificationGoogle(activity, auth)
        twitter = IdentificationTwitter(auth)

        if (activity is IdentificationCallback) {
            callback = activity
            callback.onIdentificationStart()
        } else {
            throw ClassCastException(
                "Not found IdentificationCallback. " +
                        "Please implement in your activity IdentificationCallback."
            )
        }
    }

    fun signInGoogle() {
        google.signIn()
    }

    fun checkCurrentUser() {
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    fun onIdentificationResult(requestCode: Int, data: Intent?) {
        when (requestCode) {
            IdentificationConst.GOOGLE_RC_SIGN_IN -> google.onResult(data,
                onUpdateUI = { currentUser ->
                    updateUI(currentUser)
                },
                onError = { apiError ->
                    callback.onIdentificationError(API_ERROR, apiError)
                })
            IdentificationConst.TWITTER_RC_SIGN_IN -> {

            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser == null) {
            callback.onIdentificationUserNotFound()
        } else {
            currentUser.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val idToken = task.result!!.token
                        if (idToken == null) {
                            callback.onIdentificationError(ID_TOKEN_NULL, NullPointerException("id token is null"))
                        } else {
                            callback.onIdentificationSuccess(currentUser, idToken)
                        }
                    } else {
                        val exception = task.exception
                        if (exception == null) {
                            callback.onIdentificationError(OTHER_ERROR, Exception("Unknown error"))
                        } else {
                            callback.onIdentificationError(OTHER_ERROR, exception)
                        }
                    }
                }
        }
    }
}