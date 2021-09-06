package com.vadmax.timetosleep.ui

import android.graphics.Typeface
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

class DpProvider : PreviewParameterProvider<Dp> {
    override val values = sequenceOf(300.dp)
}

private val textPaint = Paint().asFrameworkPaint().apply {
    isAntiAlias = true
    textSize = 50.sp.value
    color = android.graphics.Color.BLACK
    typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
}

@Preview
@Composable
fun Clock(
    @PreviewParameter(DpProvider::class) radius: Dp = 300.dp
) {

    Box(
        modifier = Modifier.size(radius)
    ) {
        val numbers = mutableListOf<NumberData>()
        val selectedHour = remember { mutableStateOf<NumberData?>(null) }

        ClockBackground(radius)
        HourArrow(radius, selectedHour.value)
        NumbersLayer(radius, numbers)
        TouchListener(radius, numbers) {
            selectedHour.value = it
        }
    }
}

@Composable
private fun ClockBackground(radius: Dp) {
    Canvas(
        modifier = Modifier.size(radius * 2),
        onDraw = {
            drawCircle(Color.Red, radius = size.width / 2)
        },
    )
}

@Composable
private fun NumbersLayer(radius: Dp, numbers: MutableList<NumberData>) {
    val clockRadius = 0.88f * (radius.value / 2)
    val center = radius.value / 2
    1.rangeTo(12).forEach { number ->
        val degree = Math.PI / 6 * (number - 3)

        LocalDensity.current.run {
            val x =
                (center + cos(degree) * clockRadius).toFloat().dp - textPaint.textSize.toDp() / 2
            val y =
                (center + sin(degree) * clockRadius).toFloat().dp - textPaint.textSize.toDp() / 2
            val pxX = (x.toPx() + textPaint.textSize / 2)
            val pxY = (y.toPx() + textPaint.textSize / 2)
            numbers.add(NumberData(number, pxX, pxY))

            Number(
                x.value,
                y.value,
                number = number,
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun TouchListener(
    radius: Dp,
    numbers: List<NumberData>,
    selectHour: (hour: NumberData) -> Unit
) {
    Box(
        modifier = Modifier
            .size(radius * 2)
            .pointerInteropFilter { event ->
                val selectedHour = numbers.find { numberData ->
                    val minX = numberData.x - textPaint.textSize
                    val maxX = numberData.x + textPaint.textSize
                    val minY = numberData.y - textPaint.textSize / 2
                    val maxY = numberData.y + textPaint.textSize / 2
                    return@find event.x in minX..maxX && event.y in minY..maxY
                }
                selectedHour?.let { selectHour(it) }
                true
            }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Number(x: Float, y: Float, number: Int) {
    Canvas(
        modifier = Modifier
            .offset(
                (x).dp,
                (y).dp,
            )
            .size(textPaint.textSize.dp),
        onDraw = {
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    number.toString(),
                    size.width / 2 - textPaint.measureText(number.toString()) / 2,
                    size.height / 2 + textPaint.textSize /2,
                    textPaint,
                )
            }
            drawLine(
                Color.Black,
                start = Offset(0F, size.height / 2),
                end = Offset(size.width, size.height / 2)
            )
        },
    )
}

@Composable
private fun HourArrow(radius: Dp, numberData: NumberData?) {
    numberData ?: return
    val arrowWidth: Float
    val arrowColor = Color.Green
    with(LocalDensity.current) {
        arrowWidth = 2.dp.toPx()
    }
    val end by animateOffsetAsState(
        targetValue = Offset(numberData.x, numberData.y + textPaint.textSize * 0.15F),
        animationSpec = TweenSpec(200, easing = LinearEasing),
    )
    Canvas(
        modifier = Modifier
            .size(radius * 2),
        onDraw = {
            val start = Offset(size.width / 2, size.height / 2)
            drawLine(
                color = arrowColor,
                strokeWidth = arrowWidth,
                start = start,
                end = end,
            )
            drawCircle(
                color = arrowColor,
                radius = textPaint.textSize * 0.8F,
                center = end
            )
        },
    )
}

private data class NumberData(val number: Int, val x: Float, val y: Float)
