package es.jgm1997.mgfisiobook

import es.jgm1997.shared.push.PushTokenProvider

class IosPushTokenProvider : PushTokenProvider {
    override suspend fun getPushToken(): String? = IosPushTokenHolder.token


    override fun platform(): String = "ios"

    override fun appVersion(): String? = IosPushTokenHolder.appVersion
}