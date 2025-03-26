package com.nudge.appsflyertest

import android.util.Log

object TestLog {
    private const val TAG = "AppsFlyerTestTag"

    fun messageLog(message: String) {
        Log.e(TAG, message)
    }
}