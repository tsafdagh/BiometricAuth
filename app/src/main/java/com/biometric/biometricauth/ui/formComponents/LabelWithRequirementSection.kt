package com.biometric.biometricauth.ui.formComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.biometric.biometricauth.R


@Composable
fun LabelWithRequirement(modifier: Modifier = Modifier, labelText: String) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        LabelText(text = labelText)
        RequirementBox()
    }
}

@Composable
fun RequirementBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                color = colorResource(id = R.color.blue_transparent2),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 8.dp)

    ) {
        Text(text = stringResource(id = R.string.required), color = Color.Blue)
    }
}

@Preview
@Composable
fun preview() {
    LabelWithRequirement(modifier = Modifier, labelText = "Label")
}