package com.biometric.biometricauth

import androidx.core.graphics.ColorUtils

object ColorUtils2 {

    fun darkenColor(colorRgbr:Int):Int{
       return ColorUtils.blendARGB(colorRgbr, android.graphics.Color.BLACK, 0.02f)
    }
}