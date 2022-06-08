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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

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
        val json =
            "{attempts_left=500.0, questions=[{question={id=18730320-a74f-49cd-b3d6-c18fbb93d208, name={locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=<p>Questions are always Radios. A question will have a minimum of 2 options, and maximum of 4. They can also include basic <strong>HTML in the question </strong><i><strong>text</strong></i>.</p>\n" +
                    "\n" +
                    "<p>True is the correct answer</p>}}, options=[{locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=True}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=False}], index=0.0}, {question={id=193ba828-791a-4e12-beb8-b08e146cc8c4, name={locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=<p>The correct answer is 2</p>}}, options=[{locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=1}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=2}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=3}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=4}], index=1.0}, {question={id=40eb2c7c-a836-47ad-b578-736a96116004, name={locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam elementum, nunc quis placerat mollis, augue ex faucibus tellus, ac luctus ante erat eget erat. Aenean velit arcu, mattis mattis congue ac, congue quis lectus?</p>}}, options=[{locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=Answer A}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=Answer B}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=Answer C}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=Answer D}], index=2.0}, {question={id=3ccc261a-5273-4c2e-9037-17a29866a1d5, name={locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=<p>Nullam elementum, nunc quis placerat mollis, augue ex faucibus tellus, ac luctus ante erat eget erat?</p>}}, options=[{locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=1}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=2}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=3}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=4}], index=3.0}, {question={id=dd1d0d93-84be-499b-8015-a7f0bb64c6b9, name={locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=<p><strong>Lorem</strong> ipsum dolor sit amet, consectetur adipiscing elit. <strong>Nullam</strong> elementum, nunc quis <strong>placerat</strong> mollis, augue ex faucibus tellus</p>\n" +
                    "\n" +
                    "<ul><li>1</li><li>2</li></ul>\n" +
                    "\n" +
                    "<p>ASDASDASDAS</p>\n" +
                    "\n" +
                    "<p><i>m dolor sit amet, consectetur adipiscing</i></p>}}, options=[{locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=Answer A}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=Answer B}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=Answer C}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=All of the above}], index=4.0}, {question={id=2e770187-955e-4511-8375-0ade14ac9851, name={locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=<p>Nullam elementum, nunc quis placerat mollis, augue ex faucibus tellus, ac luctus ante erat eget erat?</p>}}, options=[{locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sagittis ipsum ex, et tristique purus pharetra ut. Suspendisse in gravida quam. Aenean malesuada est sem, in vestibulum ipsum}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sagittis ipsum ex, et tristique purus pharetra ut. Suspendisse in gravida quam. Aenean malesuada est sem, in vestibulum ipsum malesuada vitae. Aliquam iaculis id nisl non tempus. Vivamus sed sollicitudin ex, pulvinar sodales mi. Etiam at lorem vel ligula condimentum feugiat a vitae enim.}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=C}, {locale=a7a93d41-1d48-4ded-a3bd-e7cfbc1bf348, value=D}], index=5.0}]}"

        val mapper = jacksonObjectMapper()
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)

        val questionObject = mapper.readValue<DataObject>(json)
        println("Test 1")

        println(questionObject)
        Toast.makeText(this, questionObject.toString(), Toast.LENGTH_SHORT).show()
    }

}

@Composable
fun ViewContent(viewModel: AuthenticationViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        val questionList by viewModel.questionListLiveData.observeAsState(mutableListOf())

        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {

            questionList.forEach { question ->

                QuestionSection(
                    question = question,
                    viewModel = viewModel
                )
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
    viewModel: AuthenticationViewModel
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
                                viewModel.setSelection(questionId = question.id, answer = text)
                            }
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
                            viewModel.setSelection(questionId = question.id, answer = text)
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