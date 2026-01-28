package es.jgm1997.mgfisiobook.viewmodels.auth

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import es.jgm1997.mgfisiobook.core.models.UserInfo
import es.jgm1997.mgfisiobook.core.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UserProfileState {
    object Idle : UserProfileState()
    object Loading : UserProfileState()
    data class Success(val profile: UserInfo) : UserProfileState()
    data class Error(val message: String) : UserProfileState()
}

class UserProfileViewModel(private val repository: AuthRepository) : ScreenModel {
    private val _state = MutableStateFlow<UserProfileState>(UserProfileState.Idle)
    val state: StateFlow<UserProfileState> = _state

    fun load() {
        screenModelScope.launch {
            _state.value = UserProfileState.Loading

            try {
                val profile = repository.profile()
                _state.value = UserProfileState.Success(profile)
            } catch (e: Exception) {
                _state.value = UserProfileState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun logout(onSuccess: () -> Unit) {
        screenModelScope.launch {
            try {
                repository.logout()
            } catch (_: Exception) {
            }
            onSuccess()
        }
    }
}