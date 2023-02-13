package com.cashwalklabs.appsflyertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AFInAppEventType
import com.appsflyer.AppsFlyerLib
import com.appsflyer.adrevenue.AppsFlyerAdRevenue
import com.appsflyer.adrevenue.adnetworks.generic.MediationNetwork
import com.appsflyer.adrevenue.adnetworks.generic.Scheme
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.cashwalklabs.appsflyertest.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
    }

    private fun initView() {
        binding.run {
            btnPurchase.setOnClickListener {
                purchaseEvent("amazon", 2000)
                showToast("btnPurchase")
            }

            btnLockscreen.setOnClickListener {
                lockScreenEvent()
                showToast("btnLockscreen")
            }

            btnAdRevenue.setOnClickListener {
                val map = HashMap<String, String>()
                map[Scheme.COUNTRY] = "US"
                map[Scheme.PLACEMENT] = "test"

                appsFlyerAdRevenue("test", MediationNetwork.customMediation, Currency.getInstance(Locale.US), 0.01, map)
                showToast("btnAdRevenue")
            }

            btnElse.setOnClickListener {
                val map = HashMap<String, Any>()
                map[AFInAppEventParameterName.LEVEL] = 3
                map[AFInAppEventParameterName.CONTENT] = "semin"

                appsFlyerInAppEvent("custom_event_2", map)
                showToast("btnInApp")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun purchaseEvent(giftCard: String, coin: Int) {
        val map = HashMap<String, Any>()
        map[AFInAppEventParameterName.CONTENT] = giftCard
        map[AFInAppEventParameterName.PRICE] = coin
        appsFlyerInAppEvent(AFInAppEventType.PURCHASE, map)
    }

    private fun lockScreenEvent() {
        appsFlyerInAppEvent("lockscreen_coin_event", emptyMap())
    }

    private fun appsFlyerInAppEvent(eventName: String, eventValue: Map<String, Any>) {
        AppsFlyerLib.getInstance().logEvent(this, eventName, eventValue, object: AppsFlyerRequestListener{
            override fun onSuccess() {
                TestLog.messageLog("logEvent: onSuccess")
            }

            override fun onError(p0: Int, p1: String) {
                TestLog.messageLog("logEvent: onError: $p0 $p1")
            }
        })
    }

    private fun appsFlyerAdRevenue(monetizationNetwork: String, mediationNetwork: MediationNetwork, eventRevenueCurrency: Currency, eventRevenue: Double, nonMandatory: Map<String, String>) {
        AppsFlyerAdRevenue.logAdRevenue(monetizationNetwork, mediationNetwork, eventRevenueCurrency, eventRevenue, nonMandatory)
    }
}