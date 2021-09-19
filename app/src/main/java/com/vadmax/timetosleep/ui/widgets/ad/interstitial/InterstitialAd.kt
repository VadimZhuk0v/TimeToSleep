package com.vadmax.timetosleep.ui.widgets.ad.interstitial

import android.content.Context
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.utils.AdUtils
import com.vadmax.timetosleep.utils.composable.showRealAd
import timber.log.Timber

private const val TEST_ADMOB_INTERSTITIAL_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
var intAd: InterstitialAd? = null

private fun setInterstitialCallback(
    context: Context,
) {
    intAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            super.onAdDismissedFullScreenContent()
            loadInterstitialAd(context)
        }
    }
}

fun loadInterstitialAd(context: Context) {
    val request = AdUtils.createBuilder()
    val unit = interstitialAdUnit(context)
    InterstitialAd.load(
        context, unit, request,
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Timber.w("Interstitial ad loading was failed: ${adError.message}")
                intAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Timber.d("Interstitial is loaded")
                intAd = interstitialAd
                setInterstitialCallback(context)
            }
        }
    )
}

fun interstitialAdUnit(context: Context): String {
    return if (showRealAd) {
        context.getString(R.string.admob_unit_interstitial)
    } else {
        TEST_ADMOB_INTERSTITIAL_UNIT_ID
    }
}
