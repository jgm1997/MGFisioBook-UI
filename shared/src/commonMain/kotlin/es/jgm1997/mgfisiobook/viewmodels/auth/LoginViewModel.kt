package es.jgm1997.mgfisiobook.viewmodels.auth

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.repositories.AuthRepository
import es.jgm1997.mgfisiobook.core.auth.AuthState
import es.jgm1997.mgfisiobook.core.models.AuthToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ScreenModel {
    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state

    fun login(email: String, password: String) {
        _state.value = LoginState.Loading

        screenModelScope.launch {
            try {
                val user = authRepository.login(email, password)
                AuthState.setToken(user)
                _state.value = LoginState.Success(user)
            } catch (e: Exception) {
                _state.value = LoginState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val user: AuthToken) : LoginState()
    data class Error(val message: String) : LoginState()
}