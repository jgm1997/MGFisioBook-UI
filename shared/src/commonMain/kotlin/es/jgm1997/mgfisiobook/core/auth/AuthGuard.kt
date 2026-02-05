package es.jgm1997.mgfisiobook.core.auth

import com.appstractive.jwt.JWT
import es.jgm1997.mgfisiobook.shared.auth.JwtParser
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.exp
import kotlin.time.Clock
import kotlin.time.Instant

fun isAuthenticated(): Boolean {
    return AuthState.token.value != null
}

fun hasRole(vararg roles: String): Boolean {
    val token = AuthState.token.value ?: return false
    return roles.contains(token.role)
}

fun isExpired(): Boolean {
    val token = AuthState.token.value ?: return true
    val exp = Instant.fromEpochSeconds(
        JwtParser.extract(token.accessToken, "exp")!!.toLong()
    )
    return Clock.System.now() >= exp
}