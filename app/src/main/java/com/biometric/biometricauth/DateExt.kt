package com.biometric.biometricauth

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*


/**
 * Permet de formater la date et renvoie avec le mot
 * toDay(ayjourdhui si la date correspond à celle
 * du jours courant
 * **/
fun Date.formatDateWithToDay(context: Context, fromat: String = "dd MMM yyy"): String {
    val todayDate = this.convertDateToSpecificStringFormat("dd MMM yyy")
    val sendDate = this.convertDateToSpecificStringFormat("dd MMM yyy")
    if (todayDate == sendDate) {
        return "${context.getString(R.string.today)}: ${
            this.convertDateToSpecificStringFormat(
                "kk:mm"
            )
        }"
    }
    return this.convertDateToSpecificStringFormat(fromat)
}
/**
 * to format date to a specific format like have only the 3 first letters of a month to have custom date
 * @param date the date to format
 * @param format the format of date that who want
 * @return the date formatted
 */
fun Date.convertDateToSpecificStringFormat(format: String = "dd MMM yyyy kk:mm"): String {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(this)
}

