package com.biometric.biometricauth.ui.formComponents

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.biometric.biometricauth.R
import java.util.*

@Composable
fun SelectDateButton(modifier: Modifier = Modifier, onDateSelected: (date: Date) -> Unit) {

    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()
    val mDate = remember { mutableStateOf("") }

    val mDatePickerDialog = DatePickerDialog(
        mContext,

        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            mCalendar.set(mYear, mMonth, mDay)
            onDateSelected(mCalendar.time)
            Toast.makeText(mContext, mDate.value, Toast.LENGTH_SHORT).show()
        }, mYear, mMonth, mDay
    )
    mDatePickerDialog.datePicker.maxDate = Date().time

    Box(
        modifier = modifier
            .background(
                color = colorResource(id = R.color.ligth_grey),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                mDatePickerDialog.show()
            }
            .padding(horizontal = 14.dp, vertical = 8.dp)

    ) {
        Text(text = "Select Date", color = Color.White, fontSize = 14.sp)
    }
}