package com.biometric.biometricauth.ui.authComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.biometric.biometricauth.R

@Composable
fun BiometricAuthSwitcher(modifier: Modifier, onBiometricAuthUsed: () -> Unit) {
    val checked = remember { mutableStateOf(false) }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        Text(text = stringResource(id = R.string.use_biometric), fontSize = 14.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = checked.value,
            onCheckedChange = {
                checked.value = it
                if (checked.value) {
                    onBiometricAuthUsed()
                }
            }
        )
    }
}
