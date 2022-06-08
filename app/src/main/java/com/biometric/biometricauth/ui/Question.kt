package com.biometric.biometricauth.ui

data class DataObject(
    var attempts_left:Double,
    var questions:List<Question>
)

data class Question(
    var id:String,
    var name: QuestionName,
    var options:List<OptionEntity>
)
data class OptionEntity(
    var locale:String,
    var value:String)

data class QuestionName(
    var locale: String,
    var value:String)
