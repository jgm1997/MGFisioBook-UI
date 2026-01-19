package es.jgm1997.mgfisiobook.core.auth

fun isAuthenticated(): Boolean {
    return AuthState.token.value != null
}

fun hasRole(vararg roles: String): Boolean {
    val token = AuthState.token.value?: return false
    return roles.contains(token.role)
}