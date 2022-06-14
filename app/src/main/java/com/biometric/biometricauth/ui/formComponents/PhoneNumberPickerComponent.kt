package com.biometric.biometricauth.ui.formComponents

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.togitech.ccp.component.TogiCountryCodePicker
import com.togitech.ccp.data.utils.checkPhoneNumber
import com.togitech.ccp.data.utils.getDefaultLangCode
import com.togitech.ccp.data.utils.getDefaultPhoneCode
import com.togitech.ccp.data.utils.getLibCountries

@Composable
fun PhoneNumberPickerComponent(phoneNumber: String, modifier: Modifier= Modifier, onPhoneNumberEdited:(phone:String) ->Unit) {
    val getDefaultLangCode = getDefaultLangCode() // Auto detect language
    val getDefaultPhoneCode = getDefaultPhoneCode() // Auto detect phone code : +90
    var phoneCode by rememberSaveable { mutableStateOf(getDefaultPhoneCode) }
    var defaultLang by rememberSaveable { mutableStateOf(getDefaultLangCode) }
    var verifyText by remember { mutableStateOf("") }
    var isValidPhone by remember { mutableStateOf(true) }

    TogiCountryCodePicker(
        modifier = modifier,
                pickedCountry = {
            phoneCode = it.countryPhoneCode
            defaultLang = it.countryCode
        },
        defaultCountry = getLibCountries().single { it.countryCode == defaultLang },
        focusedBorderColor = MaterialTheme.colors.primary,
        unfocusedBorderColor = Color.Transparent,
        dialogAppBarTextColor = Color.Black,
        dialogAppBarColor = Color.White,
        error = isValidPhone,
        text = phoneNumber,
        onValueChange = { onPhoneNumberEdited(it) }
    )

    val fullPhoneNumber = "$phoneCode${phoneNumber}"
    val checkPhoneNumber = checkPhoneNumber(
        phone = phoneNumber,
        fullPhoneNumber = fullPhoneNumber,
        countryCode = defaultLang
    )
/*
    verifyText = if (checkPhoneNumber) {
        isValidPhone = true
        "Phone Number Correct"
    } else {
        isValidPhone = false
        "Phone Number is Wrong"
    }*/
}
