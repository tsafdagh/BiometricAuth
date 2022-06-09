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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biometric.biometricauth.R
import com.biometric.biometricauth.ui.enums.AuthenticationMode
import com.biometric.biometricauth.ui.enums.PasswordRequirements
import com.biometric.biometricauth.ui.formEvens.MyFormState
import com.biometric.biometricauth.ui.stateEvents.AuthenticationEvent
import com.biometric.biometricauth.ui.uiStates.AuthenticationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthenticationViewModel : ViewModel() {

    val uiState = MutableStateFlow(AuthenticationState())
    val uiFormState = MutableStateFlow(MyFormState())

    @RequiresApi(Build.VERSION_CODES.Q)
    fun handleEvent(authenticationEven: AuthenticationEvent) {
        when (authenticationEven) {
            is AuthenticationEvent.ToggleAuthenticationMode -> {
                toggleAuthenticationMode()
            }
            is AuthenticationEvent.EmailChanged -> {
                updateEmail(authenticationEven.emailAddress)
            }
            is AuthenticationEvent.PasswordChanged -> {
                updatePassword(authenticationEven.password)
            }
            is AuthenticationEvent.Authenticate -> {
                authenticate()
            }
            is AuthenticationEvent.ErrorDismissed -> {
                dismissError()
            }
            is AuthenticationEvent.UseBiometricAuth ->{
                launchBiometric(application =authenticationEven.application )
            }
        }
    }


    private fun toggleAuthenticationMode() {
        val authenticationMode = uiState.value.authenticationMode

        val newAuthenticationMode = if (authenticationMode == AuthenticationMode.SIGN_IN) {
            AuthenticationMode.SIGN_UP
        } else {
            AuthenticationMode.SIGN_IN
        }
        uiState.value = uiState.value.copy(authenticationMode = newAuthenticationMode)
    }

    private fun updateEmail(email: String) {
        uiState.value = uiState.value.copy(email = email)
    }

    private fun updatePassword(password: String) {
        val requirements = mutableListOf<PasswordRequirements>()
        if (password.length > 7) {
            requirements.add(PasswordRequirements.EIGHT_CHARACTERS)
        }
        if (password.any { it.isUpperCase() }) {
            requirements.add(PasswordRequirements.CAPITAL_LETTER)
        }
        if (password.any { it.isDigit() }) {
            requirements.add(PasswordRequirements.NUMBER)
        }
        uiState.value = uiState.value.copy(
            password = password,
            passwordRequirements = requirements.toList()
        )
    }

    private fun authenticate(){
        uiState.value = uiState.value.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000L)

            withContext(Dispatchers.Main) {
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = "Something went wrong!"
                )
            }
        }
    }

    private fun dismissError() {
        uiState.value = uiState.value.copy(
            error = null
        )
    }

    private var cancellationSignal: CancellationSignal? = null

    private val authentificationCallback: BiometricPrompt.AuthenticationCallback =
        @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)

                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = "Authentication Succeeded!"
                )
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)

                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = "Authentication error code: $errorCode"
                )
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }

            override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                super.onAuthenticationHelp(helpCode, helpString)
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkBiometricSupport(application:Application): Boolean {

        val keyGuardManager = application.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyGuardManager.isDeviceSecure) {
            return true
        }

        if (ActivityCompat.checkSelfPermission(
                application,
                Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }

        return application.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun launchBiometric(application: Application) {
        if (checkBiometricSupport(application)) {
            val biometricPrompt = BiometricPrompt.Builder(application)
                .apply {
                    setTitle(application.getString(R.string.prompt_info_title))
                    setSubtitle(application.getString(R.string.prompt_info_subtitle))
                    setDescription(application.getString(R.string.prompt_info_description))
                    setConfirmationRequired(true)
                    setNegativeButton(
                        application.getString(R.string.prompt_info_use_app_password),
                        application.mainExecutor
                    ) { _, _ ->
                        Toast.makeText(
                            application,
                            "Biometric authentication cancelled",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.build()


            biometricPrompt.authenticate(
                getCancellationSignal(application),
                application.mainExecutor,
                authentificationCallback
            )
        }
    }

    private fun getCancellationSignal(application:Application): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Toast.makeText(application, "Authentication called signal", Toast.LENGTH_SHORT).show()
        }
        return cancellationSignal as CancellationSignal
    }
}