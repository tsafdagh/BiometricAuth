package com.biometric.biometricauth.ui.formComponents

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.biometric.biometricauth.R

@Composable
fun DropdownMenuComponent(modifier: Modifier = Modifier,items:List<String>, onItemSelected: (item: String) -> Unit) {
    val expanded = remember { mutableStateOf(false) }
    val selectedIndex = remember { mutableStateOf(0) }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.Center)
                .padding(all = 0.dp),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded.value = true
                    }
                    .background(shape = RoundedCornerShape(8.dp), color = Color.DarkGray)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = items[selectedIndex.value], color = Color.White)
                Icon(painter = painterResource(id = R.drawable.ic_baseline_navigate_next_24), contentDescription = null)
            }

            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)
                    .border(BorderStroke(width = 1.dp, color = Color.Black))
                    .shadow(elevation = 2.dp),
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        selectedIndex.value = index
                        expanded.value = false
                        onItemSelected(s)
                    }) {
                        Text(text = s, color = Color.White)
                    }
                }
            }
        }

    }
}