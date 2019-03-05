package com.engineeringidea.identification.identification

import com.google.firebase.auth.FirebaseUser

/**
 * Используем данный Interface в связке с Identification.init(this)
 * Где this это activity у которого должен быть данный interface.
 * Если этого интерфейса не будет то мы получим исключение.
 */
interface IdentificationCallback {

    /**
     * Если пользователь уже зарегистрирован или он нажал на кнопку регистрации
     * и прошел успешно авторизацию то будет вызван данный метод.
     */
    fun onIdentificationUserFound(currentUser: FirebaseUser, token: String)

    /**
     * Если пользователь не был найден.
     */
    fun onIdentificationUserNotFound()

    /**
     * Если произошла ошибка то будет вызван данный метод.
     * @see IdentificationConst
     * В классе выше содержатся errorCode
     */
    fun onIdentificationError(errorCode: Int, error: Exception)

}