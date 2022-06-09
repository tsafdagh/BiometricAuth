package com.biometric.biometricauth.ui.formComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RadioComponent(modifier: Modifier = Modifier,radioOptions: List<String>, onRadioOptionSelected: (option: String) -> Unit) {

    val (selectedOption, onOptionSelected) = remember { mutableStateOf("") }

    Column(modifier = modifier) {
        radioOptions.forEachIndexed { index, text ->

            val shape = when {
                index == 0 -> {
                    RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                }
                index + 1 == radioOptions.size -> {
                    RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                }
                else -> {
                    RoundedCornerShape(bottomStart = 0.dp, bottomEnd = 0.dp)
                }
            }

            Row(
                Modifier
                    .padding(top = 1.dp)
                    .fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            onRadioOptionSelected(text)
                        }
                    )
                    .background(
                        color = Color.DarkGray,
                        shape = shape
                    )
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    modifier = Modifier.padding(all = Dp(value = 6F)),
                    onClick = {
                        onOptionSelected(text)
                        onRadioOptionSelected(text)
                    }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }

}
