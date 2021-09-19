package com.vadmax.timetosleep.ui.widgets.ad.heaser

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.vadmax.timetosleep.BuildConfig
import com.vadmax.timetosleep.utils.AdUtils

@Composable
fun HeaderAd() {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = {
            FrameLayout(it).apply {
                addView(createAdView(it, BuildConfig.AD_HEADER_UNIT))
            }
        },
    )
}

private fun createAdView(context: Context, unit: String): View {
    return AdView(context).apply {
        adSize = AdSize.FULL_BANNER
        adUnitId = unit
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
        ).apply {
            gravity = Gravity.CENTER
        }
        loadAd(AdUtils.createBuilder())
    }
}
