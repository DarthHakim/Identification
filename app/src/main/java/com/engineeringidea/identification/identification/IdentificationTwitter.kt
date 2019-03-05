package com.engineeringidea.identification.identification

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.engineeringidea.identification.identification.IdentificationConst.TWITTER_CONSUMER_KEY
import com.engineeringidea.identification.identification.IdentificationConst.TWITTER_CONSUMER_SECRET
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.TwitterAuthProvider
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton

class IdentificationTwitter(
    private val activity: Activity,
    private val auth: FirebaseAuth,
    private val app: IdentificationApp
) {

    private lateinit var twitterButton: TwitterLoginButton

    init {
        val config = TwitterConfig.Builder(activity)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(TwitterAuthConfig(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET))
            .debug(true)
            .build()

        Twitter.initialize(config)
    }

    fun initUI(twitterButton: TwitterLoginButton) {
        this.twitterButton = twitterButton
        this.twitterButton.callback =
            object : Callback<TwitterSession>() {
                override fun success(result: Result<TwitterSession>) {
                    firebaseAuthWithTwitter(result.data)
                }

                override fun failure(exception: TwitterException) {
                    app.onUpdateUI(null)
                    app.logError("twitterLogin: failure.", exception)
                }
            }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        twitterButton.onActivityResult(requestCode, resultCode, data)
    }

    private fun firebaseAuthWithTwitter(session: TwitterSession) {
        app.logInfo("firebaseAuthWithTwitter: $session")

        val credential = TwitterAuthProvider.getCredential(
            session.authToken.token,
            session.authToken.secret
        )

        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    app.onUpdateUI(user)
                    app.logInfo("SignIn Twitter success. User: $user")
                } else {
                    app.onUpdateUI(null)
                    app.logError("SignIn Twitter error. User: null")
                }
            }
    }
}