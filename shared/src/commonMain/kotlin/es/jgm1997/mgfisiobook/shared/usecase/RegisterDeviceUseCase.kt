package es.jgm1997.mgfisiobook.shared.usecase

import es.jgm1997.mgfisiobook.core.repositories.DeviceRepository

class RegisterDeviceUseCase(private val repository: DeviceRepository) {
    suspend operator fun invoke(token: String, platform: String, appVersion: String?, userId: String) {
        repository.registerDevice(token, platform, appVersion, userId)
    }
}