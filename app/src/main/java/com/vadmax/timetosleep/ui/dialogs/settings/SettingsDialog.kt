package com.vadmax.timetosleep.ui.dialogs.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vadmax.io.data.RingerMode
import com.vadmax.timetosleep.BuildConfig
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.ui.theme.Dimens
import com.vadmax.timetosleep.ui.theme.switchColors
import com.vadmax.timetosleep.ui.widgets.dialog.BottomSheetDialog
import com.vadmax.timetosleep.utils.extentions.isAdminActive
import com.vadmax.timetosleep.utils.extentions.isNotificationAccessGranted
import com.vadmax.timetosleep.utils.extentions.navigateToLockScreenAdminPermission
import com.vadmax.timetosleep.utils.extentions.navigateToNotificationAccessSettings
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsDialog(
    viewModel: SettingsViewModel = getViewModel(),
    sheetState: ModalBottomSheetState,
) {
    val isLockScreenEnable by viewModel.lockScreenEnable.observeAsState(false)
    val isDisableBluetoothEnable by viewModel.disableBluetoothEnable.observeAsState(false)
    val isVibrationEnable by viewModel.vibrationEnable.observeAsState(true)
    val ringerMode by viewModel.ringerMode.observeAsState()
    BottomSheetDialog(sheetState) {
        SettingsContent(
            isVibrationEnable = isVibrationEnable,
            isLockScreenEnable = isLockScreenEnable,
            isDisableBluetoothEnable = isDisableBluetoothEnable,
            ringerMode = ringerMode,
            setVibrationEnable = viewModel::setVibrationEnable,
            setLockScreenEnable = viewModel::setLockScreenEnable,
            setDisableBluetoothEnable = viewModel::setDisableBluetoothEnable,
            setRingerMode = viewModel::setRingerMode,
        )
    }
}

@SuppressWarnings("LongParameterList")
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
@Composable
private fun SettingsContent(
    isVibrationEnable: Boolean = true,
    isLockScreenEnable: Boolean = true,
    isDisableBluetoothEnable: Boolean = true,
    ringerMode: RingerMode? = null,
    setVibrationEnable: (value: Boolean) -> Unit = {},
    setLockScreenEnable: (value: Boolean) -> Unit = {},
    setDisableBluetoothEnable: (value: Boolean) -> Unit = {},
    setRingerMode: (mode: RingerMode?) -> Unit = {},
) {
    val context = LocalContext.current
    Box() {
        Column {
            Item(
                text = R.string.settings_vibration,
                icon = R.drawable.ic_vibration,
                isEnable = isVibrationEnable,
                onCheckedChange = setVibrationEnable,
            )
            Item(
                text = R.string.settings_lock_screen,
                icon = R.drawable.ic_screen_lock,
                isEnable = isLockScreenEnable,
                onCheckedChange = {
                    if (it && context.isAdminActive.not()) {
                        context.navigateToLockScreenAdminPermission()
                        return@Item
                    }
                    setLockScreenEnable(it)
                },
            )
            Item(
                text = R.string.settings_disable_bluetooth,
                icon = R.drawable.ic_bluetooth,
                isEnable = isDisableBluetoothEnable,
                onCheckedChange = setDisableBluetoothEnable,
            )
            Ringer(
                ringerMode = ringerMode,
                selectMode = setRingerMode
            )
            Spacer(modifier = Modifier.height(Dimens.margin))
            Text(
                text = "${stringResource(R.string.home_version)} ${BuildConfig.VERSION_NAME}",
                modifier = Modifier.padding(Dimens.screenPadding),
            )
        }
    }
}

@Composable
private fun Item(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    isEnable: Boolean,
    onCheckedChange: (value: Boolean) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier.padding(Dimens.screenPadding),
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
                Spacer(modifier = Modifier.width(Dimens.margin2x))
                Text(
                    text = stringResource(id = text),
                )
            }
            Spacer(modifier = Modifier.width(Dimens.margin))
            Switch(
                checked = isEnable,
                onCheckedChange = onCheckedChange,
                colors = MaterialTheme.switchColors()
            )
        }
        Divider()
    }
}

@Composable
private fun Ringer(
    ringerMode: RingerMode?,
    selectMode: (mode: RingerMode?) -> Unit,
) {
    Column(modifier = Modifier.padding(Dimens.screenPadding)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_volume),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(Dimens.margin2x))
            Text(text = stringResource(id = R.string.settings_ringer_mode))
        }
        Spacer(modifier = Modifier.height(Dimens.margin))
        RingerItem(
            ringerMode = ringerMode,
            text = R.string.settings_ringer_silent,
            widgetMode = RingerMode.SILENT,
            selectMode = selectMode,
        )
        Spacer(modifier = Modifier.height(Dimens.margin2x))
        RingerItem(
            ringerMode = ringerMode,
            text = R.string.settings_ringer_vibrate,
            widgetMode = RingerMode.VIBRATE,
            selectMode = selectMode,
        )
    }
    Divider()
}

@Composable
fun RingerItem(
    ringerMode: RingerMode?,
    @StringRes text: Int,
    widgetMode: RingerMode,
    selectMode: (mode: RingerMode?) -> Unit
) {
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stringResource(text),
            modifier = Modifier.weight(1F),
        )
        Switch(
            checked = ringerMode == widgetMode,
            colors = MaterialTheme.switchColors(),
            onCheckedChange = {
                if (it && context.isNotificationAccessGranted.not()) {
                    context.navigateToNotificationAccessSettings()
                    return@Switch
                }
                selectMode(getMode(ringerMode, widgetMode, it))
            }
        )
    }
}

private fun getMode(currentMode: RingerMode?, widgetMode: RingerMode, value: Boolean): RingerMode? {
    currentMode ?: run {
        return widgetMode
    }
    return if (currentMode == widgetMode && value.not()) {
        null
    } else {
        widgetMode
    }
}
