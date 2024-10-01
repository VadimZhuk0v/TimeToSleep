package com.vadmax.timetosleep.ui.screens.home.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.EaseOutElastic
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.coreui.Shapes
import com.vadmax.timetosleep.coreui.VoidCallback
import com.vadmax.timetosleep.coreui.extensions.clickableNoRipple
import com.vadmax.timetosleep.coreui.theme.AppTheme
import com.vadmax.timetosleep.coreui.widgets.Spacer
import com.vadmax.timetosleep.ui.screens.home.support.HomeScreenScope

context(HomeScreenScope)
@Composable
fun HomeTabBar(
    onPhoneClick: VoidCallback,
    onPCClick: VoidCallback,
    isPhoneTab: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
        ) {
            Text(
                text = stringResource(R.string.home_phone),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.clickableNoRipple { onPhoneClick() },
                color = Color.White,
            )
            Indicator(
                selected = isPhoneTab,
            )
        }
        Spacer(20.dp)
        Column(
            modifier = Modifier.width(IntrinsicSize.Max),
        ) {
            Text(
                text = stringResource(R.string.home_pc),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.clickableNoRipple { onPCClick() },
                color = Color.White,
            )
            Indicator(
                selected = isPhoneTab.not(),
            )
        }
    }
}

@Composable
private fun Indicator(
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = selected,
        enter = scaleIn(
            animationSpec = tween(
                durationMillis = 300,
                easing = EaseOutBack,
            ),
        ),
        exit = scaleOut(),
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(Shapes.shape16)
                .background(Color.White),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTabBarPreview() {
    var isPhoneTab by remember { mutableStateOf(true) }
    with(HomeScreenScope) {
        AppTheme {
            HomeTabBar(
                isPhoneTab = isPhoneTab,
                onPhoneClick = { isPhoneTab = true },
                onPCClick = { isPhoneTab = false },
            )
        }
    }
}
