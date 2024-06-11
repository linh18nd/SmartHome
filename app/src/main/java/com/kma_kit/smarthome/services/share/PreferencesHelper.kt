import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_AuthToken = "auth_token"
    }

    var authToken: String?
        get() = preferences.getString(KEY_AuthToken, null)
        set(value) = preferences.edit().putString(KEY_AuthToken, value).apply()

    fun clear() {
        preferences.edit().clear().apply()

    }
}
