package com.vadmax.timetosleep.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.rememberNavController
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.VoidCallback
import com.vadmax.timetosleep.coreui.theme.AppTheme
import com.vadmax.timetosleep.coreui.widgets.Spacer
import com.vadmax.timetosleep.ui.dialogs.settings.SettingsDialog
import com.vadmax.timetosleep.ui.screens.home.support.HomeScreenScope
import com.vadmax.timetosleep.ui.screens.home.support.ListenScreenEvent
import com.vadmax.timetosleep.ui.screens.home.ui.ConnectionStatus
import com.vadmax.timetosleep.ui.screens.home.ui.HomeNavigation
import com.vadmax.timetosleep.ui.screens.pctimer.support.navigateToPCTimer
import com.vadmax.timetosleep.ui.screens.phonetimer.support.PhoneTimerScreenArgs
import com.vadmax.timetosleep.ui.screens.phonetimer.support.navigateToPhoneTimer
import com.vadmax.timetosleep.ui.widgets.iconbutton.IconButton
import com.vadmax.timetosleep.utils.LocalNavController
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

context(HomeScreenScope)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val connected by viewModel.connected.collectAsStateWithLifecycle()

    val settingsDialogVisible = remember { mutableStateOf(false) }

    HomeScreenContent(
        connected = connected,
        onSettingsClick = { settingsDialogVisible.value = true },
    )

    SettingsDialog(visible = settingsDialogVisible)
    ListenScreenEvent(viewModel.event)
}

context(HomeScreenScope)
@Composable
fun HomeScreenContent(
    onSettingsClick: VoidCallback,
    connected: Boolean,
) {
    val navController = rememberNavController()
    var isPhoneTab by remember { mutableStateOf(true) }
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
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(
                        bottom = 8.dp,
                    )
                    .navigationBarsPadding(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(16.dp)
                AnimatedVisibility(
                    visible = isPhoneTab.not(),
                    modifier = Modifier.weight(1F),
                ) {
                    ConnectionStatus(
                        connected = connected,
                    )
                }
                IconButton(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "Settings",
                    onClick = onSettingsClick,
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collectLatest {
            isPhoneTab = it.destination.hasRoute(PhoneTimerScreenArgs::class)
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
                connected = false,
            )
        }
    }
}
