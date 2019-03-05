package com.engineeringidea.identification.identification

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import com.google.android.gms.common.SignInButton
import com.twitter.sdk.android.core.identity.TwitterLoginButton

object Identification {

    @SuppressLint("StaticFieldLeak")
    lateinit var identificationApp: IdentificationApp

    /**
     * Добавляем инициализацию в onCreate для дальнейшего использования.
     * Перед setContentView(R.layout.activity_main)
     */
    fun init(activity: Activity) {
        identificationApp = IdentificationApp(activity)
        identificationApp.checkCurrentUser()
    }

    /**
     * Инициализируем кнопку для входа через Google
     * Вызвать когда UI будет установлен.
     */
    fun addSignGoogleButton(button: SignInButton) {
        identificationApp.addSignGoogleButton(button)
    }

    /**
     * Инициализируем кнопку для входа через Twitter
     * Вызвать когда UI будет установлен.
     */
    fun addSignTwitterButton(twitterButton: TwitterLoginButton) {
        identificationApp.addSignTwitterButton(twitterButton)
    }

    /**
     * Используем в активности методе onActivityResult.
     * Это дает нам возможность получить нужные данные.
     * Когда пользователь успешно прошел авторизацию.
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        identificationApp.onActivityResult(requestCode, resultCode, data)
    }

}