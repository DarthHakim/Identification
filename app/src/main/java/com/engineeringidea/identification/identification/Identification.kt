package com.engineeringidea.identification.identification

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent

object Identification {

    @SuppressLint("StaticFieldLeak")
    lateinit var identificationApp: IdentificationApp

    /**
     * Добавляем инициализацию в onCreate для дальнейшего использования.
     */
    fun init(activity: Activity) {
        identificationApp = IdentificationApp(activity)
    }

    fun initUI() {
        identificationApp.initUI()
    }

    /**
     * В onStart проверяем пользователь уже был авторизован в приложении или нет.
     */
    fun checkCurrentUser() {
        identificationApp.checkCurrentUser()
    }

    /**
     * Используем эту функцию в событие для кнопки.
     * Лучше всего использовать связку кнопка от гугл + событие onClick.
     * Добавляем данную кнопку в разметку.
     * @see com.google.android.gms.common.SignInButton
     */
    fun signInGoogle() {
        identificationApp.signInGoogle()
    }

    /**
     * Скоро
     */
    fun signInTwitter() {

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