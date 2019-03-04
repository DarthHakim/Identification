package com.engineeringidea.identification.identification

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class IdentificationGoogle(
    private val activity: Activity,
    private val auth: FirebaseAuth
) {

    private var googleSignInClient: GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(IdentificationConst.GOOGLE_WEB_CLIENT_ID)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, IdentificationConst.GOOGLE_RC_SIGN_IN)
    }

    fun onResult(
        data: Intent?,
        onUpdateUI: (user: FirebaseUser?) -> Unit,
        onError: (error: ApiException) -> Unit
    ) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account!!, onUpdateUI = onUpdateUI)
        } catch (e: ApiException) {
            onError(e)
        }
    }

    private fun firebaseAuthWithGoogle(
        acct: GoogleSignInAccount,
        onUpdateUI: (user: FirebaseUser?) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    onUpdateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    onUpdateUI(null)
                }
            }
    }
}