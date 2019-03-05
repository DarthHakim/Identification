package com.engineeringidea.identification

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.engineeringidea.identification.identification.Identification
import com.engineeringidea.identification.identification.IdentificationCallback
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseUser
import com.twitter.sdk.android.core.identity.TwitterLoginButton


class MainActivity : AppCompatActivity(), IdentificationCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Инициализируем авторизацию
        Identification.init(this)
        //Инициализируем UI
        setContentView(R.layout.activity_main)
        //Находим кнопки
        val googleButton = findViewById<SignInButton>(R.id.sign_in_google)
        val twitterButton = findViewById<TwitterLoginButton>(R.id.sign_in_twitter)
        //Добавялем их
        Identification.addSignGoogleButton(googleButton)
        Identification.addSignTwitterButton(twitterButton)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //Обратный вызов авторизации.
        Identification.onActivityResult(requestCode, resultCode, data)
    }

    override fun onIdentificationUserFound(currentUser: FirebaseUser, token: String) {
        //Пользователь найден успешно.
    }

    override fun onIdentificationUserNotFound() {
        //Пользователь не найден.
    }

    override fun onIdentificationError(errorCode: Int, error: Exception) {
        //Произошла ошибка.
    }
}