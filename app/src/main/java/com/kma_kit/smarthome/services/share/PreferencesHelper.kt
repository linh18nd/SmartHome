import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper private constructor(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    companion object {
        @Volatile
        private var INSTANCE: PreferencesHelper? = null

        fun getInstance(context: Context): PreferencesHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferencesHelper(context).also { INSTANCE = it }
            }
        }

        private const val KEY_AuthToken = "auth_token"
    }

    var authToken: String?
        get() = preferences.getString(KEY_AuthToken, null)
        set(value) = preferences.edit().putString(KEY_AuthToken, value).apply()

    fun clear() {
        preferences.edit().clear().apply()
    }
}
