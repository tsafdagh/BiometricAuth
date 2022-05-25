package com.biometric.biometricauth.ui.authStateEvents

import android.app.Application

sealed class AuthenticationEvent {

    object ToggleAuthenticationMode : AuthenticationEvent()

    class EmailChanged(val emailAddress: String) : AuthenticationEvent()

    class PasswordChanged(val password: String) : AuthenticationEvent()

    object Authenticate: AuthenticationEvent()

    object ErrorDismissed: AuthenticationEvent()

    class UseBiometricAuth(val application: Application) : AuthenticationEvent()

}