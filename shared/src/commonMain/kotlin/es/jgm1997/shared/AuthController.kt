package es.jgm1997.shared

import es.jgm1997.mgfisiobook.core.repositories.AuthRepository
import es.jgm1997.mgfisiobook.core.auth.AuthStorage
import es.jgm1997.shared.auth.AuthResult
import es.jgm1997.shared.auth.JwtParser
import es.jgm1997.shared.usecase.RegisterDeviceUseCase
import es.jgm1997.shared.push.PushTokenProvider


class AuthController(
    private val authRepository: AuthRepository,
    private val pushTokenProvider: PushTokenProvider,
    private val registerDeviceUseCase: RegisterDeviceUseCase
) {
    suspend fun login(email: String, password: String): AuthResult {
        return try {
            val token = authRepository.login(email, password)
            AuthStorage.saveToken(token)
            val userId = JwtParser.extractUserId(token.accessToken)
                ?: return AuthResult.Error("No se pudo extraer el userId del JWT")
            val pushToken = pushTokenProvider.getPushToken()
            if (pushToken != null) registerDeviceUseCase(
                pushToken, pushTokenProvider.platform(), pushTokenProvider.appVersion(), userId
            )
            AuthResult.Success(token, userId)
        } catch (e: Exception) {
            AuthResult.Error("Login failed", e)
        }
    }
}