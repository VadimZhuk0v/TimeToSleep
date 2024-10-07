package com.vadmax.timetosleep.ui.dialogs.phonesettings

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.vadmax.core.data.RingerMode
import com.vadmax.timetosleep.BuildConfig
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.extensions.clickableNoRipple
import com.vadmax.timetosleep.coreui.theme.Dimens
import com.vadmax.timetosleep.ui.widgets.appbottomsheet.AppModalBottomSheet
import com.vadmax.timetosleep.ui.widgets.divider.AppHorizontalDivider
import com.vadmax.timetosleep.ui.widgets.switch.AppSwitch
import com.vadmax.timetosleep.utils.extentions.isAdminActive
import com.vadmax.timetosleep.utils.extentions.isNotificationAccessGranted
import com.vadmax.timetosleep.utils.extentions.navigateToLockScreenAdminPermission
import com.vadmax.timetosleep.utils.extentions.navigateToNotificationAccessSettings
import org.koin.androidx.compose.koinViewModel

@Composable
fun PhoneSettingsDialog(
    visible: MutableState<Boolean>,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val isLockScreenEnable by viewModel.lockScreenEnable.collectAsState(initial = false)
    val isDisableBluetoothEnable by viewModel.disableBluetoothEnable.collectAsState(initial = false)
    val isVibrationEnable by viewModel.vibrationEnable.collectAsState(initial = true)
    val ringerMode by viewModel.ringerMode.collectAsState(initial = null)
    val soundEffectEnable by viewModel.soundEffectEnable.collectAsState()

    AppModalBottomSheet(
        visible = visible,
    ) {
        SettingsContent(
            isVibrationEnable = isVibrationEnable,
            isLockScreenEnable = isLockScreenEnable,
            isDisableBluetoothEnable = isDisableBluetoothEnable,
            ringerMode = ringerMode,
            soundEffectEnable = soundEffectEnable,
            setVibrationEnable = viewModel::setVibrationEnable,
            setLockScreenEnable = viewModel::setLockScreenEnable,
            setDisableBluetoothEnable = viewModel::setDisableBluetoothEnable,
            setRingerMode = viewModel::setRingerMode,
            onSoundEffectChange = viewModel::setSoundEffectEnable,
        )
    }
}

@SuppressWarnings("LongParameterList")
@Composable
private fun SettingsContent(
    isVibrationEnable: Boolean,
    isLockScreenEnable: Boolean,
    isDisableBluetoothEnable: Boolean,
    soundEffectEnable: Boolean,
    ringerMode: RingerMode?,
    setVibrationEnable: (value: Boolean) -> Unit,
    setLockScreenEnable: (value: Boolean) -> Unit,
    setDisableBluetoothEnable: (value: Boolean) -> Unit,
    setRingerMode: (mode: RingerMode?) -> Unit,
    onSoundEffectChange: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    CompositionLocalProvider(LocalContentColor provides Color.White) {
        Box {
            Column {
                Item(
                    text = R.string.settings_sound_effect,
                    icon = R.drawable.ic_sound_effect,
                    isEnable = soundEffectEnable,
                    onCheckedChange = onSoundEffectChange,
                )
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
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    Item(
                        text = R.string.settings_disable_bluetooth,
                        icon = R.drawable.ic_bluetooth,
                        isEnable = isDisableBluetoothEnable,
                        onCheckedChange = setDisableBluetoothEnable,
                    )
                }
                Ringer(
                    ringerMode = ringerMode,
                    selectMode = setRingerMode,
                )
                Spacer(modifier = Modifier.height(Dimens.margin))
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
    isEnable: Boolean,
    onCheckedChange: (value: Boolean) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .padding(Dimens.screenPadding)
                .clickableNoRipple {
                    onCheckedChange(isEnable.not())
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
                Spacer(modifier = Modifier.width(Dimens.margin2x))
                Text(
                    text = stringResource(id = text),
                )
            }
            Spacer(modifier = Modifier.width(Dimens.margin))
            AppSwitch(
                checked = isEnable,
                onCheckedChange = onCheckedChange,
            )
        }
        AppHorizontalDivider()
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
                contentDescription = "",
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
    AppHorizontalDivider()
}

@Composable
fun RingerItem(
    ringerMode: RingerMode?,
    @StringRes text: Int,
    widgetMode: RingerMode,
    selectMode: (mode: RingerMode?) -> Unit,
) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickableNoRipple {
            val res = ringerMode != widgetMode
            if (res && context.isNotificationAccessGranted.not()) {
                context.navigateToNotificationAccessSettings()
                return@clickableNoRipple
            }
            selectMode(getMode(ringerMode, widgetMode, res))
        },
    ) {
        Text(
            text = stringResource(text),
            modifier = Modifier.weight(1F),
        )
        AppSwitch(
            checked = ringerMode == widgetMode,
            onCheckedChange = {
                if (it && context.isNotificationAccessGranted.not()) {
                    context.navigateToNotificationAccessSettings()
                    return@AppSwitch
                }
                selectMode(getMode(ringerMode, widgetMode, it))
            },
        )
    }
}

private fun getMode(
    currentMode: RingerMode?,
    widgetMode: RingerMode,
    value: Boolean,
): RingerMode? {
    currentMode ?: run {
        return widgetMode
    }
    return if (currentMode == widgetMode && value.not()) {
        null
    } else {
        widgetMode
    }
}
