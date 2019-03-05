package com.engineeringidea.identification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.engineeringidea.identification.identification.Identification
import com.engineeringidea.identification.identification.IdentificationCallback
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseUser
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import com.twitter.sdk.android.core.identity.TwitterLoginButton

class MainActivity : AppCompatActivity(), IdentificationCallback, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Инициализируем авторизацию
        Identification.init(this)
        setContentView(R.layout.activity_main)
        //Инициализируем UI
        Identification.initUI()
        //Устанавливаем события на кнопки
        findViewById<SignInButton>(R.id.sign_in_google).setOnClickListener(this)
//        findViewById<TwitterLoginButton>(R.id.sign_in_twitter).setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        //Проверяем наличие пользователя
        Identification.checkCurrentUser()
    }

    /**
     * Когда пользователь хочет авторизоваться
     * он может нажать на одну из существующих кнопок.
     * И пройти авторизацию после этого будет вызван один из следующих методов.
     * @see onIdentificationSuccess -> Если пользователь авторизовался успешно.
     * @see onIdentificationError -> Если авторизация прошла неудачно.
     *
     * Если он закроет окно авторизации без завершения
     * авторизации то не чего вызвано не будет.
     */
    override fun onClick(v: View?) {
        when (v!!.id) {
            //Вызываем авторизацию через Google
            R.id.sign_in_google -> Identification.signInGoogle()
//            Авторизация через twitter в процессе.
//            R.id.sign_in_twitter -> Identification.signInTwitter()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Identification.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Как только будет вызван Identification.init(this)
     * Вызовется данный метод.
     * Начнется проверка пользователя.
     * В конце проверке будут вызваны 1 из 3 методов.
     * @see onIdentificationUserNotFound -> Если пользователь не найден.
     * @see onIdentificationSuccess -> Если пользователь уже есть.
     * @see onIdentificationError -> Ошибка идентификации.
     */
    override fun onIdentificationStart() {
        //Начало проверки.
    }

    override fun onIdentificationUserNotFound() {
        //Пользователь не найден.
    }

    override fun onIdentificationSuccess(currentUser: FirebaseUser, token: String) {
        //Пользователь найден успешно.
    }

    override fun onIdentificationError(errorCode: Int, error: Exception) {
        //Произошла ошибка.
    }
}