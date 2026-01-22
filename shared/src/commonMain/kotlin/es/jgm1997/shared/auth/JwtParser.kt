package es.jgm1997.shared.auth

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
object JwtParser {
    fun extractUserId(jwt: String): String? {
        val parts = jwt.split(".")
        if (parts.size != 3) return null

        return try {
            val payload = Base64.decode(parts[1]).decodeToString()
            val json = Json.parseToJsonElement(payload).jsonObject
            json["sub"]?.jsonPrimitive?.content
        } catch (_: Exception) {
            null
        }
    }
}