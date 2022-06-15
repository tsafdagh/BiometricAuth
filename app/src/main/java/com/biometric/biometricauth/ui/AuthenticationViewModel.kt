package com.biometric.biometricauth.ui

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.biometric.biometricauth.ui.formEvens.MyFormEvent
import com.biometric.biometricauth.ui.formEvens.MyFormState
import com.biometric.biometricauth.ui.uiStates.AuthenticationState
import kotlinx.coroutines.flow.MutableStateFlow

class AuthenticationViewModel : ViewModel() {

    val uiState = MutableStateFlow(AuthenticationState())

    val uiFormState = MutableStateFlow(MyFormState())

    fun handleFromEven(state: MyFormEvent) {
        when (state) {
            is MyFormEvent.TextChanged -> {
                uiFormState.value = uiFormState.value.copy(firstText = state.text)
            }
            is MyFormEvent.TextAreaChanged -> {
                uiFormState.value = uiFormState.value.copy(textArea = state.text)
            }
            is MyFormEvent.NumericChanged -> {
                uiFormState.value = uiFormState.value.copy(numericValue = state.number)
            }
            is MyFormEvent.DateChanged -> {
                uiFormState.value = uiFormState.value.copy(date = state.date)
            }
            is MyFormEvent.CheckBoxChanged -> {
                uiFormState.value = uiFormState.value.copy(isCheckBoxSelected = state.isChecked)
            }
            is MyFormEvent.CheckBox2Changed -> {
                uiFormState.value = uiFormState.value.copy(isCheckBox2Selected = state.isChecked)
            }
            is MyFormEvent.RadioButtonSelected -> {
                uiFormState.value = uiFormState.value.copy( radioOptionSelection = state.selectedValue)
            }
            is MyFormEvent.DropDownSelected -> {
                uiFormState.value = uiFormState.value.copy( dropDownSelection = state.value)
            }
            is MyFormEvent.SingleCheckBoxChanged -> {
                uiFormState.value = uiFormState.value.copy( isCheckBoxSelected = state.isChecked)
            }
            is MyFormEvent.TextControlChanged -> {
                uiFormState.value = uiFormState.value.copy( textControl = state.text)
            }
            is MyFormEvent.TextArea2Changed -> {
                uiFormState.value = uiFormState.value.copy( textArea2 = state.text)
            }
            is MyFormEvent.NumericPhoneFieldChanged -> {
                uiFormState.value = uiFormState.value.copy( numericPhone = state.text)
            }
            is MyFormEvent.NoDescriptionNumericChanged -> {
                uiFormState.value = uiFormState.value.copy( noDescriptionNumeric = state.text)
            }
            is MyFormEvent.NumericPhone2FieldChanged -> {
                uiFormState.value = uiFormState.value.copy( numericPhone2 =state.text)
            }
        }
    }

}