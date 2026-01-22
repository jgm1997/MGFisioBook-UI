package es.jgm1997.shared.push

import es.jgm1997.mgfisiobook.core.device.DeviceRepository

class RegisterDeviceUseCase(private val repository: DeviceRepository) {
    suspend operator fun invoke(token: String, platform: String, appVersion: String?, userId: String) {
        repository.registerDevice(token, platform, appVersion, userId)
    }
}