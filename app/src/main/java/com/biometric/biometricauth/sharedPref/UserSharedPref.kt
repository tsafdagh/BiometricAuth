package com.biometric.biometricauth.sharedPref

import android.content.Context
import android.content.SharedPreferences

class UserSharedPref(val context: Context) {

    private val PREFS_FILENAME = "com.biometric.biometricauth.userPrefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)


    private val KEY_USERS_IS_ALREADY_SING = "IS_USER_ALREADY_SIGNED"

    var isUserAlredySigned: Boolean
        get() = prefs.getBoolean(KEY_USERS_IS_ALREADY_SING, false)
        set(value) = prefs.edit().putBoolean(KEY_USERS_IS_ALREADY_SING, value).apply()
}