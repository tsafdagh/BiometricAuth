package com.biometric.biometricauth.ui.formEvens

import android.app.Application
import com.biometric.biometricauth.ui.stateEvents.AuthenticationEvent
import java.util.*

sealed class MyFormEvent {

    class TextChanged(val text: String) : MyFormEvent()
    class TextAreaChanged(val text: String) : MyFormEvent()
    class NumericChanged(val number: Double) : MyFormEvent()
    class DateChanged(val date: Date) : MyFormEvent()
    class CheckBoxChanged(val isChecked: Boolean) : MyFormEvent()
    class RadioButtonSelected(val selectedValue: String) : MyFormEvent()
    class DropDownSelected(val value: String) : MyFormEvent()
    class SingleCheckBoxChanged(val isChecked: Boolean) : MyFormEvent()
    class TextControlChanged(val text: String) : MyFormEvent()
    class TextArea2Changed(val text: String) : MyFormEvent()
    class NumericPhoneFieldChanged(val text: String) : MyFormEvent()
    class NoDescriptionNumericChanged(val text: String) : MyFormEvent()
    class NumericPhone2FieldChanged(val text: String) : MyFormEvent()
}
