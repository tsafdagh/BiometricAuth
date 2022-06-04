package com.biometric.biometricauth.ui

import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.biometric.biometricauth.R
import com.biometric.biometricauth.convertDateToSpecificStringFormat
import com.biometric.biometricauth.ui.theme.BiometricAuthTheme
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

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
                            topBar = { TopBar(isIconPinned = false) },
                            backgroundColor = colorResource(id = R.color.ligth_red)
                        ) {
                            MainScreen(
                                modifier = Modifier,
                                scope = scope,
                                state = bottomSheetScaffoldState,
                                backgroundColor = colorResource(id = R.color.ligth_red2),
                                date = Date(),
                                imageUrl = "https://t4.ftcdn.net/jpg/03/36/26/53/360_F_336265345_U65QKmIeAmmpaPM2C1QaQKhDG7AxoMl9.jpg",
                                "Frank Tchuisseu"
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun TopBar(isIconPinned: Boolean = true) {
        TopAppBar(
            elevation = 0.dp,
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
            val backgroundShapeColor =
                if (isIconPinned) colorResource(id = R.color.too_low_red) else Color.White
            Row(
                modifier = Modifier
                    .background(
                        color = backgroundShapeColor,
                        shape = RoundedCornerShape(22.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                if (isIconPinned) {
                    Icon(
                        modifier = Modifier.size(22.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_push_pin_24),
                        contentDescription = null
                    )
                } else {
                    Row(
                        modifier = Modifier
                            .background(
                                colorResource(id = R.color.hellow_color),
                                shape = CircleShape
                            )
                            .padding(1.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(22.dp),
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
                    }, color = textColor, fontSize = 14.sp
                )

            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class, coil.annotation.ExperimentalCoilApi::class)
    @Composable
    fun MainScreen(
        modifier: Modifier = Modifier,
        scope: CoroutineScope,
        state: BottomSheetScaffoldState,
        backgroundColor: Color = colorResource(id = R.color.ligth_red2),
        date: Date = Date(),
        imageUrl: String,
        userName: String,
        scaffoldState: ScaffoldState = rememberScaffoldState()
    ) {
        Column(
            modifier
                .fillMaxSize()
                .background(color = backgroundColor)
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7F)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 8.dp)
                        .background(
                            color = colorResource(
                                id = R.color.white_too_transparent
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.white_too_transparent),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(text = "CONTRACTOR", fontSize = 24.sp, fontWeight = FontWeight.Medium)
                        Text(
                            text = "COMPLIANCE",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W300,
                            color = colorResource(
                                id = R.color.hellow_color
                            )
                        )

                        Spacer(modifier = Modifier.width(30.dp))

                        val dateFormated = date.convertDateToSpecificStringFormat()
                        Text(text = dateFormated, fontSize = 16.sp, fontWeight = FontWeight.W300)
                        //Spacer(modifier = Modifier.width(18.dp))
                        Image(
                            painter = rememberImagePainter(data = imageUrl),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(18.dp)
                                .size(120.dp)
                                .clip(
                                    CircleShape
                                )
                        )

                        Text(
                            text = "$userName",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.W300,
                            color = Color.White
                        )

                    }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = colorResource(id = R.color.white_transparent))
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                                    .size(50.dp),
                                painter = painterResource(id = R.drawable.ic_no_svg),
                                contentDescription = null
                            )

                            Text(
                                text = "Not Compliant",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium
                            )


                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = colorResource(id = R.color.white),
                                    shape = RoundedCornerShape(
                                        bottomStart = 12.dp,
                                        bottomEnd = 12.dp
                                    )
                                )
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                                    .size(50.dp)
                                    .background(
                                        color = Color.Black,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(4.dp),
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = "S",
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Toronto",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Say Yeah!",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.LightGray
                                )
                            }

                        }
                    }

                }


                val iconList = mutableListOf<IconObject>()

                LaunchedEffect(scaffoldState.snackbarHostState ){
                    (0..41).forEach { count ->
                        iconList.add(IconObject(position = count))
                    }
                }
                var randomIndex by remember { mutableStateOf(0) }

                FlowRow(modifier = Modifier.matchParentSize(), crossAxisSpacing = 30.dp) {
                    iconList.forEach { iconItem ->
                        Icon(
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp)
                                .size(24.dp),
                            painter = if (iconItem.position != randomIndex) {
                                painterResource(id = R.drawable.ic_no_svg_with_tranparence)
                            } else {
                                painterResource(id = R.drawable.ic_no_svg)
                            },
                            contentDescription = null
                        )
                    }
                }
                Handler(Looper.getMainLooper()).postDelayed({
                    randomIndex = (0..iconList.size).random()
                }, 1000)

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