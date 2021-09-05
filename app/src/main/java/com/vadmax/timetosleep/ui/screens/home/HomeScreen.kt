package com.vadmax.timetosleep.ui.screens.home

import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.getViewModel

@Preview
@Composable
fun HomeScreen(viewModel: HomeViewModel = getViewModel()) {
    Scaffold() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue),
        ) {
            Text(viewModel.a)
            val context = LocalContext.current
            Button(onClick = { onClick(context) }) {
            }
        }
    }
}

fun onClick(context: Context) {
    val am = context.getSystemService(AudioManager::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val r = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).build()
        am.requestAudioFocus(r)
    } else {
        am.requestAudioFocus({}, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
    }
}
