package com.vadmax.timetosleep.ui.screens.pctimer.ui

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.node.Ref
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.Shapes
import com.vadmax.timetosleep.coreui.VoidCallback
import com.vadmax.timetosleep.coreui.theme.AppColors
import com.vadmax.timetosleep.coreui.theme.AppTheme
import com.vadmax.timetosleep.coreui.theme.Dimens
import com.vadmax.timetosleep.coreui.widgets.Spacer
import com.vadmax.timetosleep.ui.screens.pctimer.support.PCTimerScreenScope
import com.vadmax.timetosleep.ui.widgets.actionbutton.ActionButton
import com.vadmax.timetosleep.ui.widgets.localicon.LocalIcon
import com.vadmax.timetosleep.ui.widgets.localimage.LocalImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import qrscanner.QrScanner
import timber.log.Timber

private const val QR_ID = "QR_ID"

context(PCTimerScreenScope)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NoDeviceScreenStata(
    setServerConfig: (value: String) -> Unit,
    onInfoClick: VoidCallback,
    modifier: Modifier = Modifier,
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (cameraPermissionState.status.isGranted) {
                QRScannerState(
                    setServerConfig = setServerConfig,
                )
            } else {
                PermissionState(
                    onRequestPermissionClick = {
                        Timber.i("👆 On request permission click")
                        cameraPermissionState.launchPermissionRequest()
                    },
                )
            }
        }
        IconButton(
            onClick = onInfoClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = Dimens.margin)
                .navigationBarsPadding(),
        ) {
            LocalIcon(id = R.drawable.ic_info)
        }
    }
}

@Composable
private fun QRScannerState(
    setServerConfig: (value: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val enabled = remember { Ref<Boolean>().apply { value = true } }
    val coroutineScope = rememberCoroutineScope()
    val text = remember {
        val text = context.getString(R.string.pc_scan_qr_title)
        val arr = text.split("QR")
        buildAnnotatedString {
            append(arr.first())
            appendInlineContent(QR_ID)
            append(arr.last())
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(40.dp)
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            inlineContent = mapOf(inlineQREntry),
        )
        Spacer(20.dp)
        Box(
            contentAlignment = Alignment.Center,
        ) {
            QrScanner(
                modifier = Modifier
                    .size(300.dp)
                    .clip(Shapes.shape16),
                imagePickerHandler = {},
                onFailure = {},
                flashlightOn = false,
                onCompletion = {
                    if (enabled.value == true) {
                        enabled.value = false
                        setServerConfig(it)
                        coroutineScope.launch {
                            delay(500)
                            enabled.value = true
                        }
                    }
                },
                openImagePicker = false,
            )
            LocalImage(
                id = R.drawable.im_camera_frame,
                colorFilter = ColorFilter.tint(AppColors.cameraFrame),
                modifier = Modifier.size(325.dp),
            )
        }
    }
}

@Composable
private fun PermissionState(
    modifier: Modifier = Modifier,
    onRequestPermissionClick: VoidCallback,
) {
    val context = LocalContext.current
    val text = remember {
        buildAnnotatedString {
            append(context.getString(R.string.pc_grant_access_to_the_camera))
            append(" ")
            appendInlineContent(QR_ID)
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            textAlign = TextAlign.Center,
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            inlineContent = mapOf(inlineQREntry),
            color = Color.White,
        )
        Spacer(20.dp)
        ActionButton(
            onClick = onRequestPermissionClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = AppColors.permissionGrant,
            ),
            borderColor = AppColors.permissionGrant,
        ) {
            Text(
                text = stringResource(R.string.pc_grant_access),
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}

private val inlineQREntry = QR_ID to InlineTextContent(
    Placeholder(
        width = 30.sp,
        height = 30.sp,
        placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
    ),
) {
    LocalIcon(
        id = R.drawable.ic_qr,
        modifier = Modifier.size(30.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun PermissionStatePreview() {
    AppTheme {
        PermissionState(
            onRequestPermissionClick = {},
        )
    }
}
