package com.vadmax.timetosleep.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.rememberNavController
import com.vadmax.timetosleep.coreui.theme.AppTheme
import com.vadmax.timetosleep.coreui.widgets.Spacer
import com.vadmax.timetosleep.ui.screens.home.support.HomeScreenScope
import com.vadmax.timetosleep.ui.screens.home.support.ListenScreenEvent
import com.vadmax.timetosleep.ui.screens.home.ui.HomeNavigation
import com.vadmax.timetosleep.ui.screens.home.ui.HomeTabBar
import com.vadmax.timetosleep.ui.screens.pctimer.support.navigateToPCTimer
import com.vadmax.timetosleep.ui.screens.phonetimer.support.PhoneTimerScreenArgs
import com.vadmax.timetosleep.ui.screens.phonetimer.support.navigateToPhoneTimer
import com.vadmax.timetosleep.utils.LocalNavController
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

context(HomeScreenScope)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    HomeScreenContent()
    ListenScreenEvent(viewModel.event)
}

context(HomeScreenScope)
@Composable
fun HomeScreenContent() {
    val navController = rememberNavController()
    var isPhoneTab by remember { mutableStateOf(true) }
    Scaffold {
        Column(
            modifier = Modifier.statusBarsPadding(),
        ) {
            Spacer(20.dp)
            HomeTabBar(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                isPhoneTab = isPhoneTab,
                onPhoneClick = {
                    Timber.i("👆 Phone tab click")
                    navController.navigateToPhoneTimer()
                },
                onPCClick = {
                    Timber.i("👆 PC tab click")
                    navController.navigateToPCTimer()
                },
            )
            Box(modifier = Modifier.weight(1F)) {
                CompositionLocalProvider(LocalNavController provides navController) {
                    HomeNavigation(navController)
                }
            }
        }
        LaunchedEffect(Unit) {
            navController.currentBackStackEntryFlow.collectLatest {
                isPhoneTab = it.destination.hasRoute(PhoneTimerScreenArgs::class)
            }
        }
    }
}

@Preview
@Composable
private fun HomePreview() {
    with(HomeScreenScope) {
        AppTheme {
            HomeScreenContent()
        }
    }
}
