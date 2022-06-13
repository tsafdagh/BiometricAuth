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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
        buildTest()
    }

    fun buildTest() {

        println("Test 1")
    }

}

@Composable
fun ViewContent(viewModel: AuthenticationViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        val questionList by viewModel.questionListLiveData.observeAsState(mutableListOf())

        val canEnableSendResponse by viewModel.isBtnEnabled.observeAsState(false)

        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {

            questionList.forEachIndexed{questionIndex, question ->

                QuestionSection(
                    question = question,
                    viewModel = viewModel,
                    questionIndex=questionIndex,
                    listSize = questionList.size
                )
            }
            val context = LocalContext.current
            Button(enabled = canEnableSendResponse, onClick = {
                Toast.makeText(context
                   ,
                    "Button Clicked",
                    Toast.LENGTH_SHORT
                ).show()}) {

                Text(text = "Submit")
            }
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
fun QuestionSection(
    question: Question,
    viewModel: AuthenticationViewModel,
    listSize: Int,
    questionIndex:Int
) {

    val radioOptions = question.options.map { it.value }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 18.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HtmlText(contenText = question.name.value)
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
                            onClick = {
                                onOptionSelected(text)
                                viewModel.setSelection(
                                    questionId = question.id,
                                    answer = text,
                                    questionIndex = questionIndex,
                                    listSize
                                )
                            }
                        )
                        .background(
                            color = androidx.compose.ui.graphics.Color.DarkGray,
                            shape = shape
                        )
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        modifier = Modifier.padding(all = Dp(value = 6F)),
                        onClick = {
                            viewModel.setSelection(questionId = question.id, answer = text, questionIndex=questionIndex, listSize)
                            onOptionSelected(text)
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
        val viewModel: AuthenticationViewModel = viewModel()
        ViewContent(viewModel)
    }
}