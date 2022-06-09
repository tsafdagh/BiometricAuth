package com.biometric.biometricauth.ui.formComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@Composable
fun InputTextArea(
    modifier: Modifier = Modifier,
    text: String,
    onTextValueChanged: (text: String) -> Unit,
    onNextClicked: () -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(5.dp),
        value = text,
        onValueChange = onTextValueChanged,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Default, keyboardType = KeyboardType.Text
        ),
        maxLines = 3,
        textStyle = MaterialTheme.typography.caption,
        keyboardActions = KeyboardActions(
            onNext = {
                onNextClicked()
            }
        )
    )
}