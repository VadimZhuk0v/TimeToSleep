package com.vadmax.timetosleep.utils.previewprovider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.vadmax.core.data.AppInfo

class AppInfoListPreviewProvider : PreviewParameterProvider<List<AppInfo>> {

    override val values: Sequence<List<AppInfo>>
        get() = sequenceOf(
            listOf(
                AppInfo(
                    packageName = "com.sample.name",
                    name = "name",
                ),
            ),
        )
}
