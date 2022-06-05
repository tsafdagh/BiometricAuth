package com.biometric.biometricauth.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.biometric.biometricauth.ui.theme.BiometricAuthTheme

class MainActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: AuthenticationViewModel = viewModel()

            BiometricAuthTheme {
                // A surface container using the 'background' color from the theme
                ViewContent(viewModel = viewModel)
            }

            LaunchedEffect(key1 = "", block = {
                viewModel.mockDate()
            })
        }
    }

}

@Composable
fun ViewContent(viewModel: AuthenticationViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        //val questionList by viewModel.questionListLiveData.observeA
        val textContent =
            "Hello <b>World</b>. This <i><strike>text</strike>sentence</i> is form<b>att<u>ed</u></b> in simple html. <a href=\"https://github.com/ch4rl3x/HtmlText\">HtmlText</a>"
        Column() {
            val radioOptions = listOf("DSA", "Java", "C++")
            var selectedOption by remember { mutableStateOf("") }

            SimpleRadioButtonComponent(
                question = textContent,
                radioOptions,
                selectedOption,
                onOptionSelected = { it ->
                    selectedOption = it
                })
        }

    }
}


@Composable
fun HtmlText(modifier: Modifier = Modifier, contenText: String) {
    AndroidView(modifier = Modifier, factory = { context ->
        TextView(context).apply {
            text = HtmlCompat.fromHtml(contenText, HtmlCompat.FROM_HTML_MODE_COMPACT)
            setTextColor(Color.WHITE)
        }
    })
}

@Composable
fun SimpleRadioButtonComponent(
    question: String,
    radioOptions: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HtmlText(contenText = question)
        Column(modifier = Modifier.padding(top = 12.dp)) {
            radioOptions.forEachIndexed { index, text ->

                val shape = when {
                    index == 0 -> {
                        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                    }
                    index + 1 == radioOptions.size -> {
                        RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                    }
                    else -> {
                        RoundedCornerShape(bottomStart = 0.dp, bottomEnd = 0.dp)
                    }
                }
                Row(
                    Modifier
                        .padding(top = 1.dp)
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) }
                        )
                        .background(
                            color = androidx.compose.ui.graphics.Color.DarkGray,
                            shape = shape
                        )
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val context = LocalContext.current
                    RadioButton(
                        selected = (text == selectedOption),
                        modifier = Modifier.padding(all = Dp(value = 6F)),
                        onClick = {

                            onOptionSelected(text)
                            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                        }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BiometricAuthTheme {

        ViewContent(viewModel)
    }
}