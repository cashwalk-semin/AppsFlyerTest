package com.nudge.appsflyertest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AFInAppEventType
import com.nudge.appsflyertest.databinding.ActivityMainBinding

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
            }

            btnElse.setOnClickListener {
                wishListEvent()
                showToast("btnInApp")
            }

            btnDeepLink.setOnClickListener {
                val uri = "https://minseoksemi.onelink.me/Yhy4/dzow60cw"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
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

    private fun wishListEvent() {
        val param = AppsFlyerManager.getInAppParam(AFInAppEventParameterName.PRICE to 1234 , AFInAppEventParameterName.CONTENT_ID to "euijin")
        AppsFlyerManager.logEvent(AFInAppEventType.ADD_TO_WISH_LIST, param)
    }
}