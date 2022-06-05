package com.biometric.biometricauth.ui

data class Question(
    var id:String,
    var question: String,
    var options:ArrayList<OptionEntity>
)
data class OptionEntity(var locale:String, var value:String)