package es.jgm1997.shared.auth

import es.jgm1997.mgfisiobook.core.models.AuthToken

sealed class AuthResult {
    data class Success(val accessToken: AuthToken, val userId: String) :
        AuthResult()

    data class Error(val message: String, val cause: Throwable? = null) : AuthResult()
}