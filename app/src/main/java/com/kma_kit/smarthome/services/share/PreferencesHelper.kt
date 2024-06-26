@file:Suppress("DEPRECATION")

import android.content.Context
import android.preference.PreferenceManager
import com.kma_kit.smarthome.ui.SmartHomeApplication

class PreferencesHelper private constructor() {

    companion object {
        @Volatile
        private var INSTANCE: PreferencesHelper? = null

        fun getInstance(): PreferencesHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferencesHelper().also { INSTANCE = it }
            }
        }

        private const val KEY_AuthToken = "auth_token"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(SmartHomeApplication.getAppContext())

    var authToken: String?
        get() = preferences.getString(KEY_AuthToken, null)
        set(value) = preferences.edit().putString(KEY_AuthToken, value).apply()

    fun clear() {
        preferences.edit().clear().apply()
    }
}
