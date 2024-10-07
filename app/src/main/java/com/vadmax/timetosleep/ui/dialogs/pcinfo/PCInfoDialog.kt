package com.vadmax.timetosleep.ui.dialogs.pcinfo

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.VoidCallback
import com.vadmax.timetosleep.coreui.theme.AppColors
import com.vadmax.timetosleep.coreui.theme.Dimens
import com.vadmax.timetosleep.coreui.widgets.Spacer
import com.vadmax.timetosleep.ui.dialogs.pcinfo.support.ListenPCInfoEvent
import com.vadmax.timetosleep.ui.dialogs.pcinfo.support.PCInfoScope
import com.vadmax.timetosleep.ui.widgets.actionbutton.ActionButton
import com.vadmax.timetosleep.ui.widgets.appbottomsheet.AppModalBottomSheet
import org.koin.androidx.compose.koinViewModel

@Composable
fun PCInfoDialog(
    visible: MutableState<Boolean>,
    viewModel: PCInfoViewModel = koinViewModel(),
) {
    AppModalBottomSheet(visible = visible) {
        PCInfoContent(
            onDownloadClick = viewModel::onDownloadClick,
        )
    }

    with(PCInfoScope) {
        ListenPCInfoEvent(viewModel.event)
    }
}

@Composable
private fun PCInfoContent(onDownloadClick: VoidCallback) {
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.pc_info_title),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
        Spacer(Dimens.margin3x)
        ActionButton(
            onClick = onDownloadClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = AppColors.download,
            ),
            borderColor = AppColors.download,
        ) {
            Text(
                text = stringResource(R.string.pc_info_download),
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        Spacer(Dimens.margin3x)
        Image(
            modifier = Modifier.size(200.dp),
            painter = rememberAsyncImagePainter(
                model = R.raw.pc_info,
                imageLoader = imageLoader,
            ),
            contentDescription = null,
        )
        Spacer(Dimens.margin2x)
    }
}
