package com.biometric.biometricauth.ui.authComponents

import android.app.Application
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.biometric.biometricauth.BuildCard2
import com.biometric.biometricauth.ui.AuthenticationErrorDialog
import com.biometric.biometricauth.ui.authEnums.AuthenticationMode
import com.biometric.biometricauth.ui.authEnums.PasswordRequirements
import com.biometric.biometricauth.ui.authStateEvents.AuthenticationEvent
import com.biometric.biometricauth.ui.uiStates.AuthenticationState


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthenticationContent(
    modifier: Modifier = Modifier,
    authenticationState: AuthenticationState,
    application: Application,
    handleEvent: (event: AuthenticationEvent) -> Unit
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        BuildCard2(resources = application.resources)
        if (authenticationState.isLoading) {
            CircularProgressIndicator()
        } else {
            AuthenticationForm(
                modifier = Modifier.fillMaxSize(),
                email = authenticationState.email,
                password = authenticationState.password,
                completedPasswordRequirements =
                authenticationState.passwordRequirements,
                authenticationMode =
                authenticationState.authenticationMode,
                enableAuthentication =
                authenticationState.isFormValid(),
                onEmailChanged = {
                    handleEvent(
                        AuthenticationEvent.EmailChanged(it)
                    )
                },
                onPasswordChanged = {
                    handleEvent(
                        AuthenticationEvent
                            .PasswordChanged(it)
                    )
                },
                onAuthenticate = {
                    handleEvent(
                        AuthenticationEvent.Authenticate
                    )
                },
                onToggleMode = {
                    handleEvent(
                        AuthenticationEvent
                            .ToggleAuthenticationMode
                    )
                },
                onBiometricAuthUsed = {
                    handleEvent(
                        AuthenticationEvent.UseBiometricAuth(application =application )
                    )
                }
            )
            authenticationState.error?.let { error ->
                AuthenticationErrorDialog(
                    error = error,
                    dismissError = {
                        handleEvent(
                            AuthenticationEvent.ErrorDismissed
                        )
                    }
                )
            }
        }
    }
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
    onBiometricAuthUsed:() ->Unit
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

        if(authenticationMode == AuthenticationMode.SIGN_IN){
            BiometricAuthSwitcher(modifier = Modifier.fillMaxWidth(), onBiometricAuthUsed)
        }

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
