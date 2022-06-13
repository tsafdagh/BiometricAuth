package com.biometric.biometricauth.ui.formComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxComponent(textLabel: String, onCheckboxStatusChanged: (isChecked:Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val isChecked = remember { mutableStateOf(true) }
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                onCheckboxStatusChanged(it)
            },
            colors = CheckboxDefaults.colors(Color.Green)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = textLabel, color = Color.White)

    }
}