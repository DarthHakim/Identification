package com.engineeringidea.identification.identification

import com.google.firebase.auth.FirebaseUser

/**
 * Используем данный Interface в связке с Identification.init(this)
 * Где this это activity у которого должен быть данный interface.
 * Если этого интерфейса не будет то мы получим исключение.
 */
interface IdentificationCallback {

    /**
     * Когда Identification.init(this) вызван
     * с ним вместе будет вызван данный метод.
     */
    fun onIdentificationStart()

    /**
     * Если пользователь не был еще зарегистрирован будет вызван данный метод.
     */
    fun onIdentificationUserNotFound()

    /**
     * Если пользователь уже зарегистрирован или он нажал на кнопку регистрации
     * и прошел успешно авторизацию то будет вызван данный метод.
     */
    fun onIdentificationSuccess(currentUser: FirebaseUser, token: String)

    /**
     * Если произошла ошибка то будет вызван данный метод.
     * @see IdentificationConst
     * В классе выше содержатся errorCode
     */
    fun onIdentificationError(errorCode: Int, error: Exception)

}