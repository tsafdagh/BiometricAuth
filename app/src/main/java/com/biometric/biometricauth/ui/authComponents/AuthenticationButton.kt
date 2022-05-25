package com.biometric.biometricauth.ui.authComponents

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.biometric.biometricauth.R
import com.biometric.biometricauth.ui.authEnums.AuthenticationMode

@Composable
fun AuthenticationButton(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    enableAuthentication: Boolean,
    onAuthenticate: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = {
            onAuthenticate()
        },
        enabled = enableAuthentication
    ) {
        Text(
            text = stringResource(
                if (authenticationMode ==
                    AuthenticationMode.SIGN_IN
                ) {
                    R.string.action_sign_in
                } else {
                    R.string.action_sign_up
                }
            )
        )
    }
}
