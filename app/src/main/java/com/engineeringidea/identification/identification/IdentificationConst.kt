package com.engineeringidea.identification.identification

object IdentificationConst {

    /**
     * Тег который используется во всех классах Identification...
     */
    const val TAG = "identification"

    /**
     * В зависимости от установленного значения
     * мы либо будем получать log info либо нет.
     */
    const val LOG_ENABLED = true

    /**
     * Data
     */
    //Google
    const val GOOGLE_WEB_CLIENT_ID = "349268647597-ljksaj3aglla6c41vmm8uuuv3hnnijdj.apps.googleusercontent.com"
    //Twitter
    const val TWITTER_CONSUMER_KEY = "ajaS4GndRoZWfq80gGSwOcOSa"
    const val TWITTER_CONSUMER_SECRET = "FxdvUrVAmVHDUo4oAKfxCyx8dZ84Hr0Tz1FzOd38DNLSrCLAaP"

    /**
     * RequestCode
     */
    const val GOOGLE_RC_SIGN_IN = 139
    const val TWITTER_RC_SIGN_IN = 140

    /**
     * Errors
     */
    const val OTHER_ERROR = -4901
    const val API_ERROR = -4902
    const val ID_TOKEN_NULL = -4903

}