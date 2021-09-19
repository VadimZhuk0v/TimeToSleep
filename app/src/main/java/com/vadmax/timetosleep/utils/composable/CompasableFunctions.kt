package com.vadmax.timetosleep.utils.composable

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.vadmax.timetosleep.BuildConfig
import com.vadmax.timetosleep.R

private const val TEST_ADMOB_BANNER_UNIT_ID = "ca-app-pub-3940256099942544/6300978111"

const val IS_PRODUCTION = BuildConfig.FLAVOR == "prod"

val showRealAd = BuildConfig.DEBUG.not() && IS_PRODUCTION

@SuppressLint("ComposableNaming")
@Composable
fun headerAdUnit(): String {
    return if (showRealAd) {
        stringResource(R.string.admob_unit_header)
    } else {
        TEST_ADMOB_BANNER_UNIT_ID
    }
}
