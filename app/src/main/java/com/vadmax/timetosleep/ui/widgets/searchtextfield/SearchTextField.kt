package com.vadmax.timetosleep.ui.widgets.searchtextfield

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.R
import com.vadmax.timetosleep.ui.theme.Dimens
import com.vadmax.timetosleep.ui.theme.textFieldBackground
import com.vadmax.timetosleep.ui.widgets.iconbutton.IconButton

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchTextField(value: String, label: String? = null, onValueChange: (text: String) -> Unit) {
    var isFocused by remember { mutableStateOf(false) }
    Surface(shape = MaterialTheme.shapes.small) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.textFieldBackground)
                .fillMaxWidth()
                .padding(start = Dimens.margin, top = Dimens.margin, bottom = Dimens.margin)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1F)) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        textStyle = TextStyle(color = Color.White),
                        cursorBrush = SolidColor(Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                isFocused = it.isFocused
                            }
                    )
                    if (value == "" && label.isNullOrEmpty().not() && isFocused.not()) {
                        Text(text = label ?: "")
                    }
                }
                Spacer(modifier = Modifier.size(Dimens.margin))
                Box(modifier = Modifier.height(24.dp)) {
                    IconButton(
                        painter = painterResource(id = R.drawable.ic_clear),
                        contentDescription = "Clean",
                        onClick = {
                            onValueChange("")
                        },
                    )
                }
            }
        }
    }
}
