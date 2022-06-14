package com.biometric.biometricauth.ui.formEvens

import java.util.*

data class MyFormState(
    val firstText: String? = null,
    val textArea: String? = null,
    val numericValue:String? = null,
    val date: Date? = null,
    val isCheckBoxSelected:Boolean = false,
    val isCheckBox2Selected:Boolean = false,
    val textControl:String? = null,
    val textArea2:String? = null,
    val numericPhone:String? = null,
    val noDescriptionNumeric:String? = null,
    val numericPhone2:String? = null,
){

}
