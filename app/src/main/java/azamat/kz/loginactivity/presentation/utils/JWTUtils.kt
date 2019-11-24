package azamat.kz.loginactivity.presentation.utils

import android.util.Base64
import java.io.UnsupportedEncodingException



@Throws(Exception::class)
fun decodedGetBody(JWTEncoded: String): String? {
    try {
        val split = JWTEncoded.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//        Log.d("JWT_DECODED", "Header: " + getJson(split[0]))
//        Log.d("JWT_DECODED", "Body: " + getJson(split[1]))
        return getJson(split[1])
    } catch (e: UnsupportedEncodingException) {
        //Error
        return ""
    }
}

@Throws(UnsupportedEncodingException::class)
fun getJson(strEncoded: String): String {
    val decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE)
    return String(decodedBytes)
}