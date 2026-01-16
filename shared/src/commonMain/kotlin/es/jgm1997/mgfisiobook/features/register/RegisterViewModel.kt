package es.jgm1997.mgfisiobook.features.register

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.auth.AuthRepository
import es.jgm1997.mgfisiobook.core.models.TokenResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ScreenModel {
    private val _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state: StateFlow<RegisterState> = _state

    fun register(firstName: String, lastName: String, email: String, password: String) {
        _state.value = RegisterState.Loading

        screenModelScope.launch {
            try {
                val user = authRepository.register(email, password, firstName, lastName)
                _state.value = RegisterState.Success(user)
            } catch (e: Exception) {
                _state.value = RegisterState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    data class Success(val user: TokenResponse) : RegisterState()
    data class Error(val message: String) : RegisterState()
}