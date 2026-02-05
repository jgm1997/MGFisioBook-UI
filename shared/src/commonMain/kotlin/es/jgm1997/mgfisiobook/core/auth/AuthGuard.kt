package es.jgm1997.mgfisiobook.core.auth

import es.jgm1997.mgfisiobook.shared.auth.JwtParser
import kotlin.time.Clock
import kotlin.time.Instant

fun isAuthenticated(): Boolean {
    return AuthState.token.value?.accessToken != null
}

fun hasRole(vararg roles: String): Boolean {
    val token = AuthState.token.value ?: return false
    return roles.contains(token.role)
}

fun isExpired(): Boolean {
    val token = AuthState.token.value ?: return true
    val access = token.accessToken ?: return true
    val expClaim = JwtParser.extract(access, "exp") ?: return true
    val exp = Instant.fromEpochSeconds(expClaim.toLong())
    return Clock.System.now() >= exp
}