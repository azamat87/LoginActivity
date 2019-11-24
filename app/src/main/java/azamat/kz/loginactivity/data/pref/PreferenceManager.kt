package azamat.kz.loginactivity.data.pref

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class PreferenceManager(private val context: Context) {

    private lateinit var pref: SharedPreferences

    init {
        pref = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun getPrefString(key: String): String? {
        return pref.getString(key, "")

    }

    fun setPrefString(key: String, value: String) {
        pref.edit().putString(key, value).apply()
    }

    fun getPrefBoolean(key: String): Boolean? {
        return pref.getBoolean(key, false)

    }

    fun setPrefBoolean(key: String, value: Boolean) {
        pref.edit().putBoolean(key, value).apply()
    }

}