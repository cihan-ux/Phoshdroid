package com.phoshdroid.app

import android.app.Activity
import android.graphics.Point
import android.os.Build

/** WindowManager.currentWindowMetrics is unavailable on Android 10 (API 29). */
internal fun Activity.displaySizePx(): Pair<Int, Int> {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val bounds = windowManager.currentWindowMetrics.bounds
        return bounds.width() to bounds.height()
    }
    val point = Point()
    @Suppress("DEPRECATION")
    windowManager.defaultDisplay.getRealSize(point)
    return point.x to point.y
}
