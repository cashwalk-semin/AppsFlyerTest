package com.cashwalklabs.appsflyertest

import android.app.Application
import com.appsflyer.AppsFlyerLib
import com.appsflyer.adrevenue.AppsFlyerAdRevenue
import com.appsflyer.attribution.AppsFlyerRequestListener

class AppsFlyerTestApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initAppsFlyer()
    }

    private fun initAppsFlyer() {
        AppsFlyerLib.getInstance().init(BuildConfig.APPSFLYER_DEV_KEY, null, this)
        AppsFlyerLib.getInstance().start(this, BuildConfig.APPSFLYER_DEV_KEY, object: AppsFlyerRequestListener{
            override fun onSuccess() {
                TestLog.messageLog("initAppsFlyer:onSuccess")
            }

            override fun onError(p0: Int, p1: String) {
                TestLog.messageLog("initAppsFlyer:onError : $p0, $p1")
            }

        })
        AppsFlyerLib.getInstance().setDebugLog(true)

        val afRevenueBuilder = AppsFlyerAdRevenue.Builder(this).adEventListener { event ->
            TestLog.messageLog("adRevenue:adNetworkActionName ${event.adNetworkActionName}")
            TestLog.messageLog("adRevenue:adNetworkEventType ${event.adNetworkEventType}")
            TestLog.messageLog("adRevenue:adNetworkPayload ${event.adNetworkPayload}")
        }
        AppsFlyerAdRevenue.initialize(afRevenueBuilder.build())
    }
}