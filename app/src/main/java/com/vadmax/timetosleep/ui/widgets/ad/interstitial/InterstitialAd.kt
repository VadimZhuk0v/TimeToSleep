package com.vadmax.timetosleep.ui.widgets.ad.interstitial

import android.content.Context
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.vadmax.timetosleep.BuildConfig
import com.vadmax.timetosleep.utils.AdUtils
import timber.log.Timber

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
    InterstitialAd.load(
        context, BuildConfig.AD_INTERSTITIAL_UNIT, request,
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
