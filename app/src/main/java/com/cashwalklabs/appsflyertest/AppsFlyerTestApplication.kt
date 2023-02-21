package com.cashwalklabs.appsflyertest

import android.app.Application
import com.appsflyer.AppsFlyerLib
import com.appsflyer.adrevenue.AppsFlyerAdRevenue

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
        AppsFlyerLib.getInstance().init(BuildConfig.APPSFLYER_DEV_KEY, null, this)
        AppsFlyerLib.getInstance().start(this, BuildConfig.APPSFLYER_DEV_KEY, null)

        val afRevenueBuilder = AppsFlyerAdRevenue.Builder(this)
        AppsFlyerAdRevenue.initialize(afRevenueBuilder.build())
    }
}