package es.jgm1997.mgfisiobook.core.auth

import cafe.adriel.voyager.core.model.ScreenModel

class AuthViewModel : ScreenModel {
    fun logout() {
        AuthState.clear()
    }
}