package com.biometric.biometricauth.ui.uiStates

import com.biometric.biometricauth.ui.enums.AuthenticationMode
import com.biometric.biometricauth.ui.enums.PasswordRequirements

data class AuthenticationState(
    val authenticationMode: AuthenticationMode = AuthenticationMode.SIGN_IN,
    val email: String? = null,
    val password: String? = null,
    val passwordRequirements: List<PasswordRequirements> = listOf(),
    val isBiometricAuth:Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
) {

    fun isFormValid(): Boolean {
        return password?.isNotEmpty() == true && email?.isNotEmpty() == true
                && (authenticationMode == AuthenticationMode.SIGN_IN || passwordRequirements.containsAll(
            PasswordRequirements.values().toList()
        ))
    }
}