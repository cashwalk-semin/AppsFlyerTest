package com.nudge.appsflyertest

import android.app.Application
import android.content.Context
import android.provider.Settings
import android.telephony.TelephonyManager
import com.appsflyer.AppsFlyerLib
import com.appsflyer.deeplink.DeepLinkResult
import java.io.UnsupportedEncodingException
import java.util.UUID

class AppsFlyerTestApplication: Application() {

    companion object{
        var app: AppsFlyerTestApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        initAppsFlyer()
    }

    private fun initAppsFlyer() {
        val appsFlyerLib = AppsFlyerLib.getInstance().apply {
            init(BuildConfig.APPSFLYER_DEV_KEY, null, this@AppsFlyerTestApplication)
            waitForCustomerUserId(true)
            start(this@AppsFlyerTestApplication, BuildConfig.APPSFLYER_DEV_KEY, null)
            setCustomerIdAndLogSession(deviceUniqueId(), this@AppsFlyerTestApplication)
            subscribeForDeepLink { deepLinkResult ->
                val status = deepLinkResult.status
                val deepLinkObject = deepLinkResult.deepLink
                val deepLinkIsDeferred = deepLinkObject.isDeferred ?: false
                val deepLinkValue = deepLinkObject.deepLinkValue
                val deepLinkSub2 = deepLinkObject.getStringValue("deep_link_sub2")

                when (status) {
                    DeepLinkResult.Status.FOUND -> {
                        TestLog.messageLog("DeepLink found")
                    }

                    DeepLinkResult.Status.NOT_FOUND -> {
                        TestLog.messageLog("DeepLink not found")
                        return@subscribeForDeepLink
                    }

                    DeepLinkResult.Status.ERROR -> {
                        TestLog.messageLog("DeepLink error")
                        return@subscribeForDeepLink
                    }
                }

                try {
                    TestLog.messageLog("The DeepLink data is: $deepLinkObject")
                } catch (e: Exception) {
                    TestLog.messageLog("DeepLink data came back null")
                    TestLog.messageLog("exception::$e")
                    return@subscribeForDeepLink
                }

                if (deepLinkIsDeferred) {
                    TestLog.messageLog("Ths is a deferred DeepLink")
                } else {
                    TestLog.messageLog("Ths is a direct DeepLink")
                }

                try {
                    TestLog.messageLog("The DeepLink will route to: $deepLinkValue")
                    TestLog.messageLog("The DeepLink will sub2 to: $deepLinkSub2")
                } catch (e: Exception) {
                    TestLog.messageLog("Custom param not found in DeepLink data")
                    return@subscribeForDeepLink
                }

                TestLog.messageLog("Get DeepLink finish")
            }
        }
    }

    fun deviceUniqueId(): String {
        return secureAndroidId() ?: uuid()
    }

    private fun secureAndroidId(): String? {
        return try {
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        } catch (e: Exception) {
            null
        }
    }

    private fun uuid(): String {

        try {
            val deviceId = (getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).deviceId

            val uuid: UUID = if (deviceId != null) {
                UUID.nameUUIDFromBytes(deviceId.toByteArray(charset("UTF-8")))

            } else {
                UUID.randomUUID()
            }

            return uuid.toString()

        } catch (e: SecurityException) {
            e.printStackTrace()

        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return UUID.randomUUID().toString()
    }
}