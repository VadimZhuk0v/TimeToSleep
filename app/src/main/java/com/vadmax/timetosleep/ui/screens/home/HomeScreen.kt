package com.vadmax.timetosleep.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.VoidCallback
import com.vadmax.timetosleep.coreui.theme.AppTheme
import com.vadmax.timetosleep.coreui.widgets.Spacer
import com.vadmax.timetosleep.ui.dialogs.settings.SettingsDialog
import com.vadmax.timetosleep.ui.screens.home.support.HomeScreenScope
import com.vadmax.timetosleep.ui.screens.home.support.ListenScreenEvent
import com.vadmax.timetosleep.ui.screens.home.ui.HomeNavigation
import com.vadmax.timetosleep.ui.screens.pctimer.support.navigateToPCTimer
import com.vadmax.timetosleep.ui.screens.phonetimer.support.navigateToPhoneTimer
import com.vadmax.timetosleep.ui.widgets.iconbutton.IconButton
import com.vadmax.timetosleep.utils.LocalNavController
import org.koin.androidx.compose.koinViewModel

context(HomeScreenScope)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val settingsDialogVisible = remember { mutableStateOf(false) }

    HomeScreenContent(
        onSettingsClick = { settingsDialogVisible.value = true },
    )

    SettingsDialog(visible = settingsDialogVisible)
    ListenScreenEvent(viewModel.event)
}

context(HomeScreenScope)
@Composable
fun HomeScreenContent(onSettingsClick: VoidCallback) {
    val navController = rememberNavController()
    Scaffold {
        Column(
            modifier = Modifier.statusBarsPadding(),
        ) {
            Spacer(20.dp)
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = stringResource(id = R.string.home_phone),
                    modifier = Modifier.clickable {
                        navController.navigateToPhoneTimer()
                    },
                )
                Spacer(20.dp)
                Text(
                    text = stringResource(id = R.string.home_pc),
                    modifier = Modifier.clickable {
                        navController.navigateToPCTimer()
                    },
                )
            }
            Spacer(20.dp)
            Box(modifier = Modifier.weight(1F)) {
                CompositionLocalProvider(LocalNavController provides navController) {
                    HomeNavigation(navController)
                }
            }
            IconButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(
                        bottom = 8.dp,
                    )
                    .navigationBarsPadding(),
                painter = painterResource(id = R.drawable.ic_settings),
                contentDescription = "Settings",
                onClick = onSettingsClick,
            )
        }
    }
}

@Preview
@Composable
private fun HomePreview() {
    with(HomeScreenScope) {
        AppTheme {
            HomeScreenContent(
                onSettingsClick = { },
            )
        }
    }
}
