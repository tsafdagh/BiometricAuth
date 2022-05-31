package com.biometric.biometricauth.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.biometric.biometricauth.R
import com.biometric.biometricauth.ui.theme.BiometricAuthTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterialApi::class)
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: AuthenticationViewModel = viewModel()

            BiometricAuthTheme {
                viewModel.uiState
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
                    val scope = rememberCoroutineScope()
                    BottomSheetScaffold(
                        sheetContent = {
                            BottomSheetContent()
                        },
                        scaffoldState = bottomSheetScaffoldState,
                        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                        sheetBackgroundColor = colorResource(id = R.color.too_low_red),
                        sheetPeekHeight = 120.dp,
                        // scrimColor = Color.Red,  // Color for the fade background when you open/close the bottom sheet
                    ) {
                        Scaffold(
                            topBar = { TopBar() },
                            backgroundColor = colorResource(id = R.color.ligth_red)
                        ) {
                            MainScreen(
                                modifier = Modifier,
                                scope = scope,
                                state = bottomSheetScaffoldState
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun TopBar(isIconPinned: Boolean = false) {
        TopAppBar(
            title = {
                TopBarContaint(isIconPinned)
            },
            backgroundColor = colorResource(id = R.color.ligth_red2),
            contentColor = Color.White
        )
    }

    @Composable
    private fun TopBarContaint(isIconPinned: Boolean) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.app_name), fontSize = 18.sp)

            val backgroundShapeColor = if (isIconPinned) colorResource(id = R.color.too_low_red) else Color.White
            Row(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .background(
                        color = backgroundShapeColor,
                        shape = RoundedCornerShape(22.dp)
                    )
                    .padding(2.dp)
            ) {
                if (isIconPinned) {
                    Icon(
                        modifier = Modifier.size(22.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_push_pin_24),
                        contentDescription = null
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .background(
                                colorResource(id = R.color.hellow_color),
                                shape = CircleShape
                            )
                            .padding(1.dp),

                    ) {
                        Icon(
                            modifier = Modifier
                                .size(22.dp)
                                .align(Alignment.Center),
                            painter = painterResource(id = R.drawable.ic_baseline_push_pin_24),
                            contentDescription = null
                        )
                    }
                }
                val textColor = if (isIconPinned) Color.White else Color.Black
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isIconPinned) {
                        "Pin"
                    } else {
                        "Pinned"
                    }, color = textColor,fontSize = 14.sp
                )

            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MainScreen(
        modifier: Modifier = Modifier,
        scope: CoroutineScope,
        state: BottomSheetScaffoldState
    ) {
        Column(
            modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.teal_200_low),
                    contentColor = Color.White
                ),
                onClick = {
                    scope.launch {
                        if (state.bottomSheetState.isCollapsed) {
                            state.bottomSheetState.expand()
                        } else {
                            state.bottomSheetState.collapse()
                        }
                    }
                }) {
                if (state.bottomSheetState.isCollapsed) {
                    Text(text = "Open Bottom Sheet Scaffold")
                } else {
                    Text(text = "Close Bottom Sheet Scaffold")
                }
            }
        }
    }

}

@Composable
fun BottomSheetContent() {

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxSize()
            .background(color = colorResource(id = R.color.too_low_red)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier =
            Modifier
                .width(48.dp)
                .height(8.dp)
                .background(
                    color = colorResource(id = R.color.grey_transparent),
                    shape = RoundedCornerShape(22.dp)
                )
        )
        Spacer(modifier = Modifier.height(16.dp))
        BottomSheetContaintTopSection()
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.too_low_red2),
                    shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp),
                )
        ) {
            repeat((0..20).count()) {
                BottomSheetListItem(
                    icon = R.drawable.password_eye,
                    "random Title $it",
                    onItemClick = {})
            }
        }
    }

}

@Composable
private fun BottomSheetContaintTopSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Facility requirements", color = Color.Black)
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_person_outline_24),
            tint = Color.Black,
            contentDescription = null
        )
        Text(text = "0 %", color = Color.Black)
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_keyboard_24),
            tint = Color.Black,
            contentDescription = null
        )
        Text(text = "0 %", color = Color.Black)
    }
}

@Composable
fun BottomSheetListItem(icon: Int, title: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(title) })
            .height(55.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = title, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetListItemPreview() {
    BottomSheetListItem(icon = R.drawable.password_eye, title = "Share", onItemClick = { })
}