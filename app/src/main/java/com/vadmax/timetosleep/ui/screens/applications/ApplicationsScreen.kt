package com.vadmax.timetosleep.ui.screens.applications

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.vadmax.core.data.AppInfo
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.theme.Dimens
import com.vadmax.timetosleep.ui.screens.applications.ApplicationsScreen.route
import com.vadmax.timetosleep.ui.widgets.searchtextfield.SearchTextField
import com.vadmax.timetosleep.utils.extentions.systemInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

object ApplicationsScreen {

    const val route = "applications"
}

fun NavController.navigateToApplications(navOptionsBuilder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route) {
        navOptionsBuilder()
    }
}

@Composable
fun ApplicationsScreen(
    navController: NavController,
    viewModel: ApplicationsViewModel = koinViewModel(),
) {
    val search by viewModel.search.collectAsState(initial = "")
    val appsList by viewModel.displayedApps.collectAsState(initial = listOf())
    val selectedApps by viewModel.selectedApps.collectAsState(initial = listOf())

    ApplicationsScreenContent(
        search = search,
        appsList = appsList,
        selectedApps = selectedApps,
        searchAction = {
            viewModel.setSearch(it)
        },
        onAppTap = {
            viewModel.selectAppInfo(it)
        },
    )
}

@Preview(name = "Application screen")
@Composable
private fun ApplicationsScreenContent(
    search: String = "test",
    appsList: List<AppInfo> = listOf(),
    selectedApps: List<AppInfo> = listOf(),
    searchAction: (query: String) -> Unit = {},
    onAppTap: (appInfo: AppInfo) -> Unit = {},
) {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            Column {
                Header(
                    searchText = search,
                    onSearchEdit = searchAction,
                )
                AppsContent(
                    appsList = appsList,
                    selectedApps = selectedApps,
                    onAppTap = onAppTap,
                )
            }
        }
    }
}

@Preview(
    name = "Header",
)
@Composable
private fun Header(
    searchText: String = "text",
    onSearchEdit: (text: String) -> Unit = {},
) {
    Surface(
        shape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(0),
            topEnd = CornerSize(0),
        ),
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
        ) {
            Box(modifier = Modifier) {
                Box(modifier = Modifier.padding(Dimens.screenPadding)) {
                    SearchTextField(
                        value = searchText,
                        label = stringResource(R.string.applications_search),
                        onValueChange = onSearchEdit,
                    )
                }
            }
        }
    }
}

@Preview(
    name = "App content",
    showBackground = true,
)
@Composable
private fun AppsContent(
    appsList: List<AppInfo> = listOf(AppInfo("test", "test")),
    selectedApps: List<AppInfo> = listOf(AppInfo("test", "test")),
    onAppTap: (appInfo: AppInfo) -> Unit = {},
) {
    val context = LocalContext.current
    LazyColumn {
        appsList.forEach { appInfo ->
            val ai = try {
                appInfo.systemInfo(context)
            } catch (e: PackageManager.NameNotFoundException) {
                null
            }
            if (ai != null) {
                item {
                    AppItem(
                        appInfo = appInfo,
                        systemAppInfo = ai,
                        selectedApps = selectedApps,
                        onTap = onAppTap,
                    )
                }
            }
        }
    }
}

@Preview(name = "App item")
@Composable
private fun AppItem(
    appInfo: AppInfo = AppInfo("test", ""),
    systemAppInfo: ApplicationInfo = ApplicationInfo(),
    selectedApps: List<AppInfo> = listOf(),
    onTap: (app: AppInfo) -> Unit = {},
) {
    val context = LocalContext.current
    val isSelected = selectedApps.find { it.packageName == systemAppInfo.packageName } != null
    var icon: ImageBitmap? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        launch(Dispatchers.IO) {
            icon = context.packageManager.getApplicationIcon(appInfo.packageName).toBitmap()
                .asImageBitmap()
        }
    }
    Box(
        modifier = Modifier.clickable {
            onTap(appInfo)
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 60.dp)
                .padding(
                    start = Dimens.screenPadding,
                    end = Dimens.screenPadding,
                    top = Dimens.margin,
                    bottom = Dimens.margin,
                ),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1F)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (icon != null) {
                            Image(
                                bitmap = icon!!,
                                contentDescription = "",
                                modifier = Modifier.size(50.dp),
                            )
                        } else {
                            Spacer(modifier = Modifier.width(50.dp))
                        }
                        Spacer(modifier = Modifier.width(Dimens.margin))
                        Text(text = appInfo.name)
                    }
                }
                Spacer(modifier = Modifier.size(Dimens.margin))
                AnimatedVisibility(visible = isSelected) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round),
                        contentDescription = "mark",
                    )
                }
            }
        }
    }
}
