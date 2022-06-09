package com.biometric.biometricauth.ui.formComponents

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


@Composable
fun LabelText(modifier: Modifier = Modifier, text: String, textSize: TextUnit = 14.sp) {
    Text(modifier = modifier, text = text, color = Color.White, fontSize = textSize)
}

@Preview
@Composable
fun PreviewLabel() {
    LabelText(text = "My text label")
}