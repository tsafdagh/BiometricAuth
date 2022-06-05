package com.biometric.biometricauth.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class AuthenticationViewModel : ViewModel() {

    val questionListLiveData = MutableLiveData<List<Question>>()


    val selectedResponse = mutableMapOf<String,String>(

    )

    fun setSelection(questionId:String, answer:String){
        selectedResponse[questionId]= answer

        println("selectedResponse: $selectedResponse")
    }
    fun mockDate() {
        val textContent =
            "Hello <b>World</b>. This <i><strike>text</strike>sentence</i> is form<b>att<u>ed</u></b> in simple html. <a href=\"https://github.com/ch4rl3x/HtmlText\">HtmlText</a>"

        val questions = mutableListOf<Question>()
        (0..7).forEach {
            val optionList = mutableListOf<OptionEntity>()
            (0..(2..5).random()).forEach {
                val option = OptionEntity(
                    locale = UUID.randomUUID().toString(), value = if (it % 2 == 0) {
                        "That is true"+ (0..200).random()
                    } else {
                        "That is false"+ (0..200).random()
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