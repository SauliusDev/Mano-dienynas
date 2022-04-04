package com.sunnyoaklabs.manodienynas.core.util

/** json response messages which determine if session cookies is gotten or valid **/
object SessionValidationJsonResponses {

    // const val CREDENTIALS_CORRECT = "{\"message\":false,\"message_type\":0,\"url\":\"\\/1\\/lt\\/page\\/\\/sf\\/resolve_post\\/event\\/\\/list\"}"
    const val CREDENTIALS_CORRECT = "{\"message\":false,\"message_type\":0,\"url\":\"\\/1\\/lt\\/page\\/message_new\\/message_list\"}"
    const val CREDENTIALS_INCORRECT = "{\"message\":\"Įvesti neteisingi prisijungimo duomenys\",\"message_type\":0,\"url\":false}"
    const val SESSION_EXPIRED_HTML_PART = "<div id=\"act-user-info-text\">"
}