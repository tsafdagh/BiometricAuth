package com.biometric.biometricauth.ui

import android.Manifest
import android.app.Application
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biometric.biometricauth.R
import com.biometric.biometricauth.ui.enums.AuthenticationMode
import com.biometric.biometricauth.ui.enums.PasswordRequirements
import com.biometric.biometricauth.ui.stateEvents.AuthenticationEvent
import com.biometric.biometricauth.ui.uiStates.AuthenticationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class AuthenticationViewModel : ViewModel() {

    val questionListLiveData = MutableLiveData<List<Question>>()

    fun mockDate() {
        val textContent =
            "Hello <b>World</b>. This <i><strike>text</strike>sentence</i> is form<b>att<u>ed</u></b> in simple html. <a href=\"https://github.com/ch4rl3x/HtmlText\">HtmlText</a>"

        val questions = mutableListOf<Question>()
        (0..7).forEach {
            val optionList = mutableListOf<OptionEntity>()
            (0..(2..6).random()).forEach {
                val option = OptionEntity(
                    locale = UUID.randomUUID().toString(), value = if (it % 2 == 0) {
                        "That is true"
                    } else {
                        "That is false"
                    }
                )
                optionList.add(option)
            }

            val question = Question(id = UUID.randomUUID().toString(), question = textContent, options = optionList as ArrayList<OptionEntity>)
            questions.add(question)
        }

        questionListLiveData.value = questions
    }
}