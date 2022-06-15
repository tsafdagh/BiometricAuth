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
import androidx.compose.ui.text.input.TextFieldValue
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
import com.biometric.biometricauth.ui.formEvens.MyFormEvent
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
                        handleEvent = viewModel::handleFromEven
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
    handleEvent: (event: MyFormEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .verticalScroll(rememberScrollState())
            .background(color = Color.Black)
    ) {

        MainLabelWithDescription()

        MyMainFormContent(
            modifier = Modifier.fillMaxWidth(),
            firstText = formState.firstText ?: "",
            textArea = formState.textArea ?: "",
            numericValue = formState.numericValue ?: "",
            date = formState.date ?: Date(-1),
            isCheckBoxSelected = formState.isCheckBoxSelected,
            isCheckBox2Selected = formState.isCheckBox2Selected,
            textControl = formState.textControl ?: "",
            textArea2 = formState.textArea2 ?: "",
            numericPhone = formState.numericPhone ?: "",
            noDescriptionNumeric = formState.noDescriptionNumeric ?: "",
            numericPhone2 = formState.numericPhone2 ?: "",
            radioOptionSelection = formState.radioOptionSelection,
            onFirstTextChanged = {handleEvent(MyFormEvent.TextChanged(it))},
            onTextAreaChanged = {handleEvent(MyFormEvent.TextAreaChanged(it))},
            onNumericValueChanged = {handleEvent(MyFormEvent.NumericChanged(it))},
            onDateValueChanged = {handleEvent(MyFormEvent.DateChanged(it))},
            onCheckBoxStatusChanged = {handleEvent(MyFormEvent.CheckBoxChanged(it))},
            onCheckBox2StatusChanged = {handleEvent(MyFormEvent.CheckBox2Changed(it))},
            onRadioOptionSelectionStateChanged = {handleEvent(MyFormEvent.RadioButtonSelected(it))},
            onDropDownMenueOptionSelected ={handleEvent(MyFormEvent.DropDownSelected(it))},
            onTextControlChanged = {handleEvent(MyFormEvent.TextControlChanged(it))},
            onTextArea2Changed = {handleEvent(MyFormEvent.TextArea2Changed(it))},
            onNumericPhoneChanged = {handleEvent(MyFormEvent.NumericPhoneFieldChanged(it))},
            onDescriptionNumericChanged = {handleEvent(MyFormEvent.NoDescriptionNumericChanged(it))},
            onNumericPhone2Changed = {handleEvent(MyFormEvent.NumericPhone2FieldChanged(it))}
        )

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
    numericValue: String,
    date: Date,
    isCheckBoxSelected: Boolean,
    textControl: String,
    textArea2: String,
    radioOptionSelection:String,
    numericPhone: String,
    isCheckBox2Selected: Boolean,
    noDescriptionNumeric: String,
    numericPhone2: String,
    onFirstTextChanged: (text: String) -> Unit,
    onTextAreaChanged: (text: String) -> Unit,
    onNumericValueChanged: (number: String) -> Unit,
    onDateValueChanged: (date: Date) -> Unit,
    onCheckBoxStatusChanged: (isSelect: Boolean) -> Unit,
    onRadioOptionSelectionStateChanged: (selection: String) -> Unit,
    onDropDownMenueOptionSelected: (option: String) -> Unit,
    onCheckBox2StatusChanged: (isSelect: Boolean) -> Unit,
    onTextControlChanged: (text: String) -> Unit,
    onTextArea2Changed: (text: String) -> Unit,
    onNumericPhoneChanged: (phone: String) -> Unit,
    onDescriptionNumericChanged: (text: String) -> Unit,
    onNumericPhone2Changed: (text: String) -> Unit
) {
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
            onTextChanged = onFirstTextChanged,
            onNextClicked = {},
            text = firstText
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
            onTextValueChanged = onTextAreaChanged,
            onNextClicked = {},
            text = textArea
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
            onTextChanged = onNumericValueChanged,
            onNextClicked = {},
            text = numericValue
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
                SelectDateButton(onDateSelected = onDateValueChanged)
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
            CheckBoxComponent(
                textLabel = "Checkbox",
                onCheckboxStatusChanged = onCheckBoxStatusChanged,
                isChecked = isCheckBoxSelected
            )
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
        RadioComponent(

            radioOptions = listOf("true", "false"),
            radioOptionSelection =radioOptionSelection,
            onRadioOptionSelected = onRadioOptionSelectionStateChanged
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
        LabelWithRequiredBox(labelText = "Dropdown field")

        val items = listOf("Gorgia", "Florida", "Hawail", "Connecticust", "Alamba", "Dalawar")
        DropdownMenuComponent(
            modifier = Modifier.padding(top = 8.dp),
            items = items,
            onItemSelected = onDropDownMenueOptionSelected
        )
        LabelText(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            text = "This is the dropdown description",
            textSize = 12.sp
        )
    }


    Column(
        modifier = Modifier
            .padding(top = 14.dp)
            .fillMaxWidth()
    ) {

        CheckBoxComponent(
            isChecked = isCheckBox2Selected,
            textLabel = "Checkbox label",
            onCheckboxStatusChanged = onCheckBox2StatusChanged
        )
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
        LabelText(text = "Text control")
        FirsTextInput(
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxWidth(),
            onTextChanged = onTextControlChanged,
            onNextClicked = {},
            text = textControl
        )
        LabelText(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            text = "This is the text description without the required label. So is not required for the user!.",
            textSize = 12.sp
        )
    }

    Column(
        modifier = Modifier
            .padding(top = 14.dp)
            .fillMaxWidth()
    ) {
        LabelText(text = "text area")
        InputTextArea(
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxWidth(),
            onTextValueChanged = onTextArea2Changed,
            onNextClicked = {},
            text = textArea2
        )
        LabelText(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            text = "Text area description",
            textSize = 12.sp
        )
    }

    Column(
        modifier = Modifier
            .padding(top = 14.dp)
            .fillMaxWidth()
    ) {
        LabelText(text = "Numeric control")

        //https://stackoverflow.com/questions/67423074/jetpack-compose-setting-cursor-on-end-of-textfield
        //https://medium.com/@patilshreyas/filtering-and-modifying-text-input-in-jetpack-compose-way-8f7eeedd958
        PhoneNumberPickerComponent(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth(),
            phoneNumber = numericPhone,
            onPhoneNumberEdited = onNumericPhoneChanged
        )
        LabelText(
            modifier = Modifier
                .fillMaxWidth(),
            text = "+1(###)###-###",
            textSize = 12.sp
        )
    }

    Column(
        modifier = Modifier
            .padding(top = 14.dp)
            .fillMaxWidth()
    ) {
        LabelText(text = "No description numeric")
        NumericFieldInput(
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxWidth(),
            onTextChanged = onDescriptionNumericChanged,
            onNextClicked = {},
            text = noDescriptionNumeric
        )
    }

    Column(
        modifier = Modifier
            .padding(top = 14.dp)
            .fillMaxWidth()
    ) {
        LabelWithRequiredBox(labelText = "Numeric")
        NumericInputFieldAdd1(
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxWidth(),
            onTextChanged = onNumericPhone2Changed,
            onNextClicked = {},
            text = numericPhone2
        )
        LabelText(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            text = "+1##########",
            textSize = 12.sp
        )
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