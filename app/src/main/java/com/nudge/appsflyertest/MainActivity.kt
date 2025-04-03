package com.nudge.appsflyertest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appsflyer.AFInAppEventParameterName
import com.appsflyer.AFInAppEventType
import com.appsflyer.CreateOneLinkHttpTask
import com.appsflyer.share.ShareInviteHelper
import com.nudge.appsflyertest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
//        generateInviteUrl()
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
                val uri = getOneLinkUrl(
                    oneLinkUrl = "https://minseoksemi.onelink.me/Yhy4/dzow60cw",
                    referralCode = "seminzzang"
                )
                TestLog.messageLog("onClick::${uri}")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
            }

            btnOneLinkTest.setOnClickListener {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(inviteUrl))
//                startActivity(intent)
                generateInviteUrl()
            }
        }
    }

    private fun generateInviteUrl() {
        val linkGenerator = ShareInviteHelper.generateInviteUrl(applicationContext).apply {
            addParameter("af_custom_shortlink", "seminzzang")
            addParameter("deep_link_value" , "target_view")
            addParameter("deep_link_sub1", "promo_code")
            addParameter("deep_link_sub2", "referrer_id")
            campaign = "test_invite"
            channel = "mobile_share"
            brandDomain = "minseoksemi.onelink.me"
            addParameter("af_custom_shortlink", "afshortlink")
        }

        val listener = object: CreateOneLinkHttpTask.ResponseListener {
            override fun onResponse(p0: String?) {
                p0?.let {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                    startActivity(intent)
                }
                TestLog.messageLog("onResponse::$p0")
            }

            override fun onResponseError(p0: String?) {
                TestLog.messageLog("onResponseError::$p0")
            }

        }

        linkGenerator.generateLink(applicationContext, listener)
    }

    private fun getOneLinkUrl(oneLinkUrl: String, referralCode: String): String {
        return "$oneLinkUrl?af_force_deeplink=true&deep_link_sub2=$referralCode"
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