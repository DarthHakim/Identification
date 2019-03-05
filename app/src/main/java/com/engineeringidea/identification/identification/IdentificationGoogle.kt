package com.engineeringidea.identification.identification

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class IdentificationGoogle(
    private val activity: Activity,
    private val auth: FirebaseAuth,
    private val app: IdentificationApp
) {

    private var googleSignInClient: GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(IdentificationConst.GOOGLE_WEB_CLIENT_ID)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun initUI(button: SignInButton) {
        button.setOnClickListener { signIn() }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, IdentificationConst.GOOGLE_RC_SIGN_IN)
    }

    fun onActivityResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account!!)
        } catch (error: ApiException) {
            app.onError(IdentificationConst.API_ERROR, error)
            app.logError("API Exception.", error)
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        app.logInfo("firebaseAuthWithGoogle: $acct")

        val credential = GoogleAuthProvider.getCredential(
            acct.idToken,
            null
        )

        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    app.onUpdateUI(user)
                    app.logInfo("SignIn Google success. User: $user")
                } else {
                    app.onUpdateUI(null)
                    app.logError("SignIn Google error. User: null")
                }
            }
    }
}