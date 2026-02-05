package es.jgm1997.mgfisiobook.core.auth

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import es.jgm1997.mgfisiobook.core.models.AuthToken
import kotlinx.serialization.json.Json

object AuthStorage {
    private val settings = Settings()
    private const val KEY_TOKEN = "auth_token"

    fun saveToken(token: AuthToken) {
        val json = Json.encodeToString(token)
        settings[KEY_TOKEN] = json
    }

    fun loadToken(): AuthToken? {
        val json = settings.getStringOrNull(KEY_TOKEN) ?: return null
        val token = Json.decodeFromString<AuthToken?>(json)
        return token
    }

    fun clear() {
        settings.remove(KEY_TOKEN)
    }
}