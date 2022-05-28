package com.biometric.biometricauth.ui

import android.Manifest
import android.app.ListActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.biometric.biometricauth.R
import com.biometric.biometricauth.Utils
import com.biometric.biometricauth.entities.*
import com.biometric.biometricauth.ui.theme.BiometricAuthTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {


    var initialBitmap: CustomFile? = null
    var CustumFileList = mutableStateListOf(initialBitmap)

    @OptIn(ExperimentalMaterialApi::class)
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: CameraViewModel = viewModel()

            val cameraLauncher =
                rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                    it?.let {
                        CustumFileList.add(
                            ImageFileFromCamera(
                                type = EnumFileType.IMAGE_FROM_CAMERA.name,
                                bitmap = it
                            )
                        )
                    }
                }

            val galeryLauncher =
                rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->

                    uriList.forEach {
                        CustumFileList.add(
                            ImageFileFileFromGallery(
                                type = EnumFileType.IMAGE_FROM_GALLERY.name,
                                Uri = it
                            )
                        )
                    }
                }

            val documentLauncher =
                rememberLauncherForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) { uriList ->
                    uriList.forEach {
                        CustumFileList.add(
                            DocumentFIle(
                                type = EnumFileType.DOCUMENT_FILE.name,
                                Uri = it
                            )
                        )
                    }
                }

            BiometricAuthTheme {


                Surface(modifier = Modifier.background(color = colorResource(id = R.color.black))) {

                    val modalBottomSheetState =
                        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
                    val scope = rememberCoroutineScope()
                    ModalBottomSheetLayout(
                        sheetContent = {
                            BottomSheetContent(onItemClick = {
                                when (it) {
                                    "Camera" -> {
                                        if (shouldShowCamera.value) {
                                            cameraLauncher.launch()
                                        }
                                    }
                                    "Photo library" -> {
                                        galeryLauncher.launch("image/*")
                                    }
                                    "Document" -> {
                                        documentLauncher.launch(
                                            arrayOf(
                                                "application/pdf",
                                                "application/msword",
                                                "application/ms-doc",
                                                "application/doc",
                                                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                                                "text/plain",
                                                "application/xslt+xml",
                                            )
                                        )
                                        //https://android.googlesource.com/platform/external/mime-support/+/9817b71a54a2ee8b691c1dfa937c0f9b16b3473c/mime.types

                                    }
                                }

                                scope.launch {
                                    modalBottomSheetState.hide()
                                }
                            })
                        },
                        sheetState = modalBottomSheetState,
                        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                        sheetBackgroundColor = colorResource(id = R.color.black),
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {

                        val url =
                            "https://docs.google.com/gview?embedded=true&url=" + "https://www.openska.com/pdf/formation-reactjs.pdf"
                        AndroidView(factory = {
                            val builder = CustomTabsIntent.Builder()
                            builder.setShowTitle(true)
                            val tabIntent = builder.build()
                            View(it).apply {
                                tabIntent.launchUrl(
                                    it,
                                    Uri.parse(url)
                                )
                            }
                        })

                        /*          MainScreen(
                                      scope = scope,
                                      state = modalBottomSheetState,
                                      CustumFileList.toList()
                                  )*/
                    }
                }

            }

        }
        requestCameraPermission()
    }

    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            shouldShowCamera.value = true
        } else {
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("kilo", "Permission previously granted")
                shouldShowCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.i("kilo", "Show camera permissions dialog")

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(scope: CoroutineScope, state: ModalBottomSheetState, uriList: List<CustomFile?>) {
    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(R.color.white)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        FlowRow(
            crossAxisSpacing = 8.dp,
            mainAxisSpacing = 8.dp,
            mainAxisAlignment = MainAxisAlignment.Start
        ) {
            uriList.forEach { currentFile ->
                if (currentFile != null) {
                    ImageCardItem(customFile = currentFile)
                }
            }
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.ligth_black),
                contentColor = colorResource(id = R.color.ligth_black)
            ),
            onClick = {
                scope.launch {
                    state.show()
                }
            }) {
            Text(text = "Upload documment", color = colorResource(id = R.color.white))
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageCardItem(customFile: CustomFile) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(corner = CornerSize(6.dp)))
    ) {
        when (customFile) {
            is ImageFileFromCamera -> {
                Image(
                    bitmap = customFile.bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )
            }
            is ImageFileFileFromGallery -> {
                Image(
                    painter = rememberImagePainter(customFile.Uri),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )
            }
            is DocumentFIle -> {

                val context = LocalContext.current
                val fileExt =
                    customFile.Uri?.let { Utils.getMimeType(context = context, uri = it) } ?: ""
                Log.d("FIleExt ", "$fileExt")

                val imageResource = when (fileExt) {
                    "pdf" -> {
                        R.drawable.ic_baseline_pdf
                    }
                    "docx" -> {
                        R.drawable.ic_docs

                    }
                    else -> {
                        R.drawable.other_doc
                    }
                }
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )


            }
        }
    }
}


@Composable
fun BottomSheetContent(onItemClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.ligth_black))
            .padding(top = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .height(20.dp),
            text = "Select an upload method",
            color = colorResource(id = R.color.white)
        )

        Divider(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            color = Color.LightGray, thickness = 1.dp,
        )

        BottomSheetListItem(
            title = "Scan",
            onItemClick = { title ->
                onItemClick(title)
            })

        Divider(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            color = Color.LightGray, thickness = 1.dp,
        )

        BottomSheetListItem(
            title = "Camera",
            onItemClick = { title ->
                onItemClick(title)
            })
        Divider(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            color = Color.LightGray, thickness = 1.dp,
        )

        BottomSheetListItem(
            title = "Photo library",
            onItemClick = { title ->
                onItemClick(title)
            })
        Divider(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            color = Color.LightGray, thickness = 1.dp,
        )

        BottomSheetListItem(
            title = "Document",
            onItemClick = { title ->
                onItemClick(title)
            })
        Divider(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            color = Color.LightGray, thickness = 1.dp,
        )

    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetContentPreview() {
    BottomSheetContent(onItemClick = {})
}

@Composable
fun BottomSheetListItem(title: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(title) })
            .background(color = colorResource(id = R.color.ligth_black))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetListItemPreview() {
    BottomSheetListItem(title = "Share", onItemClick = { })
}