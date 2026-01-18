package es.jgm1997.mgfisiobook.core.auth

import es.jgm1997.mgfisiobook.core.models.AuthToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object AuthState {
    private val _token = MutableStateFlow<AuthToken?>(null)
    val token: StateFlow<AuthToken?> = _token

    fun setToken(tkn: AuthToken) {
        _token.value = tkn
    }

    fun clear() {
        _token.value = null
    }
}