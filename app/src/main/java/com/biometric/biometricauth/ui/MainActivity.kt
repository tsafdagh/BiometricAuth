package com.biometric.biometricauth.ui

import android.app.Application
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.biometric.biometricauth.R
import com.biometric.biometricauth.ui.components.*
import com.biometric.biometricauth.ui.enums.AuthenticationMode
import com.biometric.biometricauth.ui.enums.PasswordRequirements
import com.biometric.biometricauth.ui.formComponents.*
import com.biometric.biometricauth.ui.formEvens.MyFormState
import com.biometric.biometricauth.ui.stateEvents.AuthenticationEvent
import com.biometric.biometricauth.ui.theme.BiometricAuthTheme
import com.biometric.biometricauth.ui.theme.Purple500
import java.util.*

class MainActivity : ComponentActivity() {


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

                    MyMainFrom(
                        modifier = Modifier.fillMaxWidth(),
                        formState = viewModel.uiFormState.collectAsState().value,
                        application = application,
                        handleEvent = viewModel::handleEvent
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyMainFrom(
    modifier: Modifier = Modifier,
    formState: MyFormState,
    application: Application,
    handleEvent: (event: AuthenticationEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .verticalScroll(rememberScrollState())
            .background(color = Color.Black)
    ) {

        MainLabelWithDescription()

        Column(
            modifier = Modifier
                .padding(top = 14.dp)
                .fillMaxWidth()
        ) {
            LabelWithRequiredBox(labelText = "Text")
            FirsTextInput(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth(),
                onTextChanged = { },
                onNextClicked = {},
                text = ""
            )
            LabelText(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                text = "This is a text field. Field can be required -- this one is.",
                textSize = 12.sp
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 14.dp)
                .fillMaxWidth()
        ) {
            LabelWithRequiredBox(labelText = "TextArea")
            InputTextArea(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth(),
                onTextValueChanged = { },
                onNextClicked = {},
                text = ""
            )
            LabelText(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                text = "Text -- but on steroids",
                textSize = 12.sp
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 14.dp)
                .fillMaxWidth()
        ) {
            LabelWithRequiredBox(labelText = "Numeric")
            NumericFieldInput(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth(),
                onTextChanged = { },
                onNextClicked = {},
                text = ""
            )
            LabelText(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                text = "Numeric field can have validations and masks applied...",
                textSize = 12.sp
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 14.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LabelText(text = "Date")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SelectDateButton(onDateSelected = {})
                    RequirementBox(modifier = Modifier.padding(start = 12.dp))
                }
            }
            LabelText(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                text = "Opens a date picked?",
                textSize = 12.sp
            )
        }


        Column(
            modifier = Modifier
                .padding(top = 14.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CheckBoxComponent(textLabel = "Checkbox", onCheckboxSelected = {})
                RequirementBox(modifier = Modifier.padding(start = 12.dp))
            }
            LabelText(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                text = "Checkbox",
                textSize = 12.sp
            )
        }


        Column(
            modifier = Modifier
                .padding(top = 14.dp)
                .fillMaxWidth()
        ) {
            LabelWithRequiredBox(labelText = "Lorem ipsum is placeholder text commonly used in the graphic.")
            RadioComponent(radioOptions = listOf("true", "false"), onRadioOptionSelected = {})
            LabelText(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                text = "Numeric field can have validations and masks applied...",
                textSize = 12.sp
            )
        }


        Column(
            modifier = Modifier
                .padding(top = 14.dp)
                .fillMaxWidth()
        ) {
            LabelWithRequiredBox(labelText = "Dropdown field")

            val items = listOf("Gorgia", "Florida", "Hawail", "Connecticust", "Alamba", "Dalawar")
            DropdownMenuComponent(modifier = Modifier.padding(top = 8.dp), items = items, onItemSelected = {})
            LabelText(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                text = "This is the dropdown description",
                textSize = 12.sp
            )
        }

    }
}

@Composable
fun MainLabelWithDescription(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        LabelText(text = "Label")
        LabelText(
            modifier = Modifier.padding(top = 8.dp),
            text = "This is a label control",
            textSize = 12.sp
        )

    }
}

@Composable
fun MyMainFormContent(
    modifier: Modifier = Modifier,
    firstText: String,
    textArea: String,
    numericValue: Double,
    date: Date,
    isCheckBoxSelected: Boolean,
    textControl: String,
    textArea2: String,
    numericPhone: String,
    noDescriptionNumeric: String,
    numericPhone2: String,
    onFirstTextChanged: (text: String) -> Unit,
    onTextAreaChanged: (text: String) -> Unit,
    onNumericValueChanged: (number: Double) -> Unit,
    onDateValueChanged: (date: Date) -> Unit,
    onCheckBoxSelected: (isSelect: Boolean) -> Unit,
    onTextControlChanged: (text: String) -> Unit,
    onTextArea2Changed: (text: String) -> Unit,
    onNumericPhoneChanged: (phone: String) -> Unit,
    onDescriptionNumericChanged: (text: String) -> Unit,
    onNumericPhone2Changed: (text: String) -> Unit
) {

}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthenticationForm(
    modifier: Modifier = Modifier,
    authenticationMode: AuthenticationMode,
    email: String?,
    password: String?,
    completedPasswordRequirements: List<PasswordRequirements>,
    enableAuthentication: Boolean,
    onEmailChanged: (email: String) -> Unit,
    onPasswordChanged: (password: String) -> Unit,
    onToggleMode: () -> Unit,
    onAuthenticate: () -> Unit,
) {

    Spacer(modifier = Modifier.height(40.dp))
    val passwordFocusRequester = FocusRequester()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthenticationTitle(authenticationMode = authenticationMode)
        AuthenticationHeaderImage()
        EmailInput(
            modifier = Modifier.fillMaxWidth(),
            email = email ?: "",
            onEmailChanged = onEmailChanged
        ) {
            passwordFocusRequester.requestFocus()
        }
        Spacer(modifier = Modifier.height(16.dp))
        PasswordInput(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester),
            password = password,
            onPasswordChanged = onPasswordChanged,
            onDoneClicked = onAuthenticate
        )
        Spacer(modifier = Modifier.height(12.dp))

        AnimatedVisibility(
            visible = authenticationMode ==
                    AuthenticationMode.SIGN_UP
        ) {
            PasswordRequirementsComponent(modifier = Modifier, completedPasswordRequirements)
        }

        Spacer(modifier = Modifier.height(12.dp))

        AuthenticationButton(
            enableAuthentication = enableAuthentication,
            authenticationMode = authenticationMode,
            onAuthenticate = onAuthenticate
        )

        Spacer(modifier = Modifier.padding(10.dp))
        Spacer(modifier = Modifier.weight(1f))

        ToggleAuthenticationModeComponent(
            modifier = Modifier.fillMaxWidth(),
            authenticationMode = authenticationMode,
            toggleAuthentication = {
                onToggleMode()
            }
        )
    }
}


@Composable
fun BioMetricScreen(onClick: () -> Unit) {
    val context = LocalContext.current
    val emailVal = remember { mutableStateOf("") }
    val passwordVal = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val checked = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Purple500),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "BioMetric FingerPrint",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AuthenticationHeaderImage()

            Spacer(modifier = Modifier.padding(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.LightGray),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(top = 6.dp),
                    text = "LogIn",
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.h1,
                )

                Spacer(modifier = Modifier.padding(15.dp))

                OutlinedTextField(
                    value = emailVal.value,
                    onValueChange = { emailVal.value = it },
                    label = { Text(text = "Email Address") },
                    placeholder = { Text(text = "Email Address") },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )

                //if (state.emailError != null) {
                //   Text(text = state.emailError, color = MaterialTheme.colors.error)
                // }

                OutlinedTextField(
                    value = passwordVal.value,
                    onValueChange = { passwordVal.value = it },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.password_eye),
                                contentDescription = "password eye",
                                tint = if (passwordVisibility.value) Purple500 else Color.Gray
                            )
                        }
                    },
                    label = { Text(text = "Password") },
                    placeholder = { Text(text = "Password") },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password") },
                    singleLine = true,
                    visualTransformation = if (passwordVisibility.value)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )

                // if (state.passwordError != null) {
                //   Text(text = state.passwordError, color = MaterialTheme.colors.error)
                // }
                Text(
                    text = stringResource(id = R.string.forgot_pw),
                    color = if (!isSystemInDarkTheme()) Color.Black.copy(alpha = 0.7f)
                    else Color.Gray,
                )

                Spacer(modifier = Modifier.padding(10.dp))

                Button(
                    onClick = {
                        if (emailVal.value.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Please enter email address!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (passwordVal.value.isEmpty()) {
                            Toast.makeText(context, "Please enter password!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(context, "Logged Successfully!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.login), fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.padding(20.dp))

            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BiometricAuthTheme {

        val viewModel: AuthenticationViewModel = viewModel()

        //BioMetricScreen(onClick = {})
/*        AuthenticationContent(
            modifier = Modifier.fillMaxWidth(),
            authenticationState = viewModel.uiState.collectAsState().value,
            handleEvent = viewModel::handleEvent,
            application = application
        )*/
    }
}