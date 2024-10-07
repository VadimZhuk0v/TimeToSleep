package com.vadmax.timetosleep.ui.dialogs.pcsettings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.BuildConfig
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.VoidCallback
import com.vadmax.timetosleep.coreui.extensions.clickableNoRipple
import com.vadmax.timetosleep.coreui.theme.Dimens
import com.vadmax.timetosleep.coreui.widgets.Spacer
import com.vadmax.timetosleep.ui.widgets.appbottomsheet.AppModalBottomSheet
import com.vadmax.timetosleep.ui.widgets.divider.AppHorizontalDivider
import com.vadmax.timetosleep.ui.widgets.localicon.LocalIcon
import org.koin.androidx.compose.koinViewModel

@Composable
fun PCSettingsDialog(
    visible: MutableState<Boolean>,
    viewModel: PCSettingsViewModel = koinViewModel(),
) {
    AppModalBottomSheet(
        visible = visible,
    ) {
        SettingsContent(
            onOpenSourceClick = viewModel::onOpenSourceClick,
            onUnpairClick = {
                visible.value = false
                viewModel.onUnpairClick()
            },
        )
    }
}

@SuppressWarnings("LongParameterList")
@Composable
private fun SettingsContent(
    onUnpairClick: VoidCallback,
    onOpenSourceClick: VoidCallback,
) {
    CompositionLocalProvider(LocalContentColor provides Color.White) {
        Box {
            Column {
                Item(
                    text = R.string.settings_unpair,
                    icon = R.drawable.ic_unpair,
                    onClick = onUnpairClick,
                )
                Spacer(Dimens.margin)
                OpenSource(
                    onClick = onOpenSourceClick,
                )
                Text(
                    text = "${stringResource(R.string.home_version)} ${BuildConfig.VERSION_NAME}",
                    modifier = Modifier.padding(Dimens.screenPadding),
                )
            }
        }
    }
}

@Composable
private fun Item(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    onClick: VoidCallback,
) {
    Column {
        Row(
            modifier = Modifier
                .padding(Dimens.screenPadding)
                .clickableNoRipple {
                    onClick()
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1F),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "",
                )
                Spacer(Dimens.margin2x)
                Text(
                    text = stringResource(id = text),
                )
            }
            Spacer(Dimens.margin)
        }
        AppHorizontalDivider()
    }
}

@Composable
private fun OpenSource(onClick: VoidCallback) {
    Row(
        modifier = Modifier
            .padding(Dimens.screenPadding)
            .clickableNoRipple { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.weight(1F),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LocalIcon(
                id = R.drawable.ic_github,
                modifier = Modifier.size(24.dp),
                contentDescription = "Open Source",
            )
            Spacer(Dimens.margin2x)
            Text(
                text = stringResource(R.string.settings_open_source),
            )
        }
        Spacer(Dimens.margin)
    }
}
