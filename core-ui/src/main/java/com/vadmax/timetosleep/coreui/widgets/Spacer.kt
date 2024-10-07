package com.vadmax.timetosleep.coreui.widgets

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun RowScope.Spacer(width: Dp) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(width))

@Composable
fun ColumnScope.Spacer(height: Dp) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(height))

@Composable
fun ColumnScope.Spacer(weight: Float) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(weight))
