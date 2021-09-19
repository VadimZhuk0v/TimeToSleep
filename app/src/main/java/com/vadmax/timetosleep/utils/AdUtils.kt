package com.vadmax.timetosleep.utils

import androidx.core.os.bundleOf
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest

object AdUtils {

    fun createBuilder(): AdRequest = AdRequest.Builder().apply {
        addNetworkExtrasBundle(
            AdMobAdapter::class.java,
            bundleOf("max_ad_content_rating" to "G"),
        )
    }.build()
}
