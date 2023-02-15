package com.cashwalklabs.appsflyertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AFInAppEventType
import com.appsflyer.adrevenue.adnetworks.AppsFlyerAdNetworkEventType
import com.appsflyer.adrevenue.adnetworks.generic.MediationNetwork
import com.appsflyer.adrevenue.adnetworks.generic.Scheme
import com.cashwalklabs.appsflyertest.databinding.ActivityMainBinding
import java.util.*

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
                val param = AppsFlyerManager.getAdParam("event_name" to "event_name", Scheme.AD_TYPE to AppsFlyerAdNetworkEventType.BANNER.toString(), Scheme.AD_UNIT to "lucky_box", Scheme.COUNTRY to "US")

                AppsFlyerManager.logAdRevenue("test", MediationNetwork.customMediation, Currency.getInstance(Locale.US), 0.01, param)
                AppsFlyerManager.logAdRevenue("APS", MediationNetwork.ironsource, Currency.getInstance(Locale.US), 0.02, param)
                showToast("btnAdRevenue")
            }

            btnElse.setOnClickListener {
                val param = AppsFlyerManager.getInAppParam(AFInAppEventParameterName.LEVEL to 3, AFInAppEventParameterName.CONTENT to "semin")

                AppsFlyerManager.logEvent("custom_event_2", param)
                showToast("btnInApp")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun purchaseEvent(giftCard: String, coin: Int) {
        val param = AppsFlyerManager.getInAppParam(AFInAppEventParameterName.CONTENT to giftCard, AFInAppEventParameterName.PRICE to coin)
        AppsFlyerManager.logEvent(AFInAppEventType.PURCHASE, param)
    }

    private fun lockScreenEvent() {
        AppsFlyerManager.logEvent("lockscreen_coin_event")
    }
}