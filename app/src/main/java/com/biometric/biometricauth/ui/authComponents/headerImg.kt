package com.biometric.biometricauth.ui.authComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.biometric.biometricauth.R

@Composable 
fun AuthenticationHeaderImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.login_illustration),
        contentDescription = "Login image",
        modifier = Modifier
            .width(250.dp)
            .height(250.dp),
        contentScale = ContentScale.Fit
    )
}