package com.biometric.biometricauth

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat

class PhoneNumberTransformation() : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        val formattedText = formatAmountOrMessage(text.text)

        val offsetMapping = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                if (originalText.isNotEmpty()) {
                    val commas = formattedText.count { it == ',' }
                    return when {
                        offset <= 1 -> offset
                        offset <= 3 -> if (commas >= 1) offset + 1 else offset
                        offset <= 5 -> if (commas == 2) offset + 2 else offset + 1
                        else -> 8
                    }
                }
                return offset
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (originalText.isNotEmpty()) {
                    val commas = formattedText.count { it == ',' }
                    return when (offset) {
                        8, 7 -> offset - 2
                        6 -> if (commas == 1) 5 else 4
                        5 -> if (commas == 1) 4 else if (commas == 2) 3 else offset
                        4, 3 -> if (commas >= 1) offset - 1 else offset
                        2 -> if (commas == 2) 1 else offset
                        else -> offset
                    }
                }
                return offset
            }
        }

        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = offsetMapping
        )
    }


    fun formatAmountOrMessage(
        input: String
    ): String =  DecimalFormat("+1#,##,###").format(input.toDouble())
}