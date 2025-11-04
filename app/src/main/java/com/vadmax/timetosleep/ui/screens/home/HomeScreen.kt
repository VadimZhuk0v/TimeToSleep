package com.vadmax.timetosleep.ui.screens.home

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.vadmax.timetosleep.coreui.VoidCallback
import com.vadmax.timetosleep.coreui.theme.AppTheme
import com.vadmax.timetosleep.coreui.widgets.Spacer
import com.vadmax.timetosleep.ui.screens.home.support.HomeScreenScope
import com.vadmax.timetosleep.ui.screens.home.support.ListenScreenEvent
import com.vadmax.timetosleep.ui.screens.home.ui.HomeNavigation
import com.vadmax.timetosleep.ui.screens.home.ui.HomeTabBar
import com.vadmax.timetosleep.ui.screens.pctimer.support.navigateToPCTimer
import com.vadmax.timetosleep.ui.screens.phonetimer.support.PhoneTimerScreenArgs
import com.vadmax.timetosleep.ui.screens.phonetimer.support.navigateToPhoneTimer
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalPermissionsApi::class)
context(_: HomeScreenScope)
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val notificationPermission = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    val navController = rememberNavController()
    var isPhoneTab by remember { mutableStateOf(true) }

    HomeScreenContent(
        navController = navController,
        isPhoneTab = isPhoneTab,
        onPCClick = viewModel::onPCClick,
        onPhoneClick = viewModel::onPhoneClick,
    )
    ListenScreenEvent(
        event = viewModel.event,
        navigatePC = { navController.navigateToPCTimer() },
        navigatePhone = { navController.navigateToPhoneTimer() },
    )
    LaunchedEffect(Unit) {
        if (notificationPermission.status.isGranted.not()) {
            notificationPermission.launchPermissionRequest()
        }
    }
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collectLatest {
            isPhoneTab = it.destination.hasRoute(PhoneTimerScreenArgs::class)
        }
    }
}

context(_: HomeScreenScope)
@Composable
fun HomeScreenContent(
    navController: NavHostController,
    onPCClick: VoidCallback,
    onPhoneClick: VoidCallback,
    isPhoneTab: Boolean,
) {
    Scaffold {
        Column(
            modifier = Modifier.statusBarsPadding(),
        ) {
            Spacer(20.dp)
            HomeTabBar(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                isPhoneTab = isPhoneTab,
                onPhoneClick = onPhoneClick,
                onPCClick = onPCClick,
            )
            Box(modifier = Modifier.weight(1F)) {
                HomeNavigation(navController)
            }
        }
    }
}

@Preview
@Composable
private fun HomePreview() {
    with(HomeScreenScope) {
        AppTheme {
            HomeScreenContent(
                isPhoneTab = true,
                navController = rememberNavController(),
                onPhoneClick = {},
                onPCClick = {},
            )
        }
    }
}
