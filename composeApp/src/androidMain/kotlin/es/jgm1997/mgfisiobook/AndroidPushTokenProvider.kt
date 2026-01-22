package es.jgm1997.mgfisiobook

import android.content.Context
import com.google.firebase.messaging.FirebaseMessaging
import es.jgm1997.shared.push.PushTokenProvider
import kotlinx.coroutines.tasks.await

class AndroidPushTokenProvider(private val context: Context) : PushTokenProvider {
    override suspend fun getPushToken(): String? {
        return try {
            FirebaseMessaging.getInstance().token.await()
        } catch (_: Exception) {
            null
        }
    }

    override fun platform(): String = "android"

    override fun appVersion(): String? {
        return try {
            val info = context.packageManager.getPackageInfo(context.packageName, 0)
            info.versionName
        } catch (_: Exception) {
            null
        }
    }
}