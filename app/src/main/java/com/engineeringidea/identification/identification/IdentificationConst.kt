package com.engineeringidea.identification.identification

import com.google.android.gms.common.api.ApiException

object IdentificationConst {

    /**
     * Data
     */
    const val GOOGLE_WEB_CLIENT_ID = "349268647597-ljksaj3aglla6c41vmm8uuuv3hnnijdj.apps.googleusercontent.com"

    /**
     * RequestCode
     */
    const val GOOGLE_RC_SIGN_IN = 5101
    const val TWITTER_RC_SIGN_IN = 5102

    /**
     * Errors
     *
     * API_ERROR
     * @see ApiException
     */
    const val OTHER_ERROR = -4901
    const val API_ERROR = -4902
    const val ID_TOKEN_NULL = -4903

}