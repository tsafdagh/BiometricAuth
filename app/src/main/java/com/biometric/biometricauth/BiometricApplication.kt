package com.biometric.biometricauth

import android.app.Application
import com.biometric.biometricauth.sharedPref.UserSharedPref

class BiometricApplication :Application() {

    companion object{
        lateinit var userPref: UserSharedPref
    }

    override fun onCreate() {
        super.onCreate()
        userPref = UserSharedPref(this)


    }
}