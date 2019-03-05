package com.engineeringidea.identification.identification

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.engineeringidea.identification.R
import com.engineeringidea.identification.identification.IdentificationConst.TWITTER_CONSUMER_KEY
import com.engineeringidea.identification.identification.IdentificationConst.TWITTER_CONSUMER_SECRET
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.TwitterAuthProvider
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig

class IdentificationTwitter(
    private val activity: Activity,
    private val auth: FirebaseAuth,
    private val app: IdentificationApp
) {

    private lateinit var twitterButton: TwitterLoginButton

    init {
//        Twitter.initialize(activity)

        val config = TwitterConfig.Builder(activity)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(TwitterAuthConfig(TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET))
            .debug(true)
            .build()
        Twitter.initialize(config)
    }

    fun initUI () {
        twitterButton = activity.findViewById(R.id.sign_in_twitter)
        twitterButton.callback =
            object : Callback<TwitterSession>() {
                override fun success(result: Result<TwitterSession>) {
                    Log.d("myLogs", "twitterLogin:success$result")
                    handleTwitterSession(result.data)
                }

                override fun failure(exception: TwitterException) {
                    Log.w("myLogs", "twitterLogin:failure", exception)
                    app.updateUI(null)
                }
            }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        twitterButton.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleTwitterSession(
        session: TwitterSession
    ) {
        Log.d("myLogs", "handleTwitterSession:$session")

        val credential = TwitterAuthProvider.getCredential(
            session.authToken.token,
            session.authToken.secret
        )

        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("myLogs", "signInWithCredential:success")
                    val user = auth.currentUser
                    app.updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("myLogs", "signInWithCredential:failure", task.exception)
                    app.updateUI(null)
                }
            }
    }
}