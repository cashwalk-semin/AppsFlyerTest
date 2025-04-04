package com.nudge.appsflyertest

import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import java.util.*

/***
 * AppsFlyer 이벤트 트래킹을 위한 매니저
 */
object AppsFlyerManager {

    /***
     * In-App Event param 생성
     * vararg Pair -> Map 변환
     */
    fun getInAppParam(vararg pairs: Pair<String, Any>): Map<String, Any> {
        val map = HashMap<String, Any>()
        pairs.map {
            map.put(it.first, it.second)
        }
        return map
    }

    /***
     * In-App Event Tracking
     */
    fun logEvent(eventName: String, eventValue: Map<String, Any> = emptyMap()) {
        AppsFlyerTestApplication.app?.let { application ->
            AppsFlyerLib.getInstance().logEvent(application, eventName, eventValue, object: AppsFlyerRequestListener {
                //todo remove listener
                override fun onSuccess() {
                    TestLog.messageLog("logEvent: onSuccess")
                }

                override fun onError(p0: Int, p1: String) {
                    TestLog.messageLog("logEvent: onError: $p0 $p1")
                }
            })
        }
    }
}