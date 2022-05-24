package com.biometric.biometricauth

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils

@Composable
@Preview
fun PreviewCard() {
    //BuildCard()
}

@Composable
fun BuildCard(modifier: Modifier = Modifier, resources:Resources) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {

        /*     val triangleShape = GenericShape { size, _ ->
                 moveTo(0f, 0f)
                 lineTo(size.width, 0f )

                 lineTo(size.width / 1f, size.height / 1f)
             }*/

        val triangleShape = GenericShape { size, _ ->
            moveTo(size.width, 0f)

            lineTo(size.width, size.height)
            lineTo(0f, size.height)
        }

        Box(
            modifier = Modifier
                .fillMaxHeight(1f)
                .fillMaxWidth(1f)
                .shadow(10.dp, shape = RoundedCornerShape(CornerSize(10.dp)))
                .background(
                    color = colorResource(id = R.color.teal_200),
                    shape = RoundedCornerShape(CornerSize(10.dp))
                )
                .padding(0.dp)
        ) {

           // colorResource(id = R.color.teal_200_low)
            val colorRgbr = ResourcesCompat.getColor(resources , R.color.teal_200, null)
            val myDarken = ColorUtils.blendARGB(colorRgbr, android.graphics.Color.BLACK, 0.02f)
            Surface(
                modifier =
                Modifier.fillMaxSize(),
                elevation = 4.dp,
                shape = triangleShape,
                color = Color(myDarken)
            ) {
            }
/*            val color = colorResource(id = R.color.teal_200_low_center)
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {

                // Fetching width and height for
                // setting start x and end y
                val canvasWidth = size.width
                val canvasHeight = size.height

                // drawing a line between start(x,y) and end(x,y)
                drawLine(
                    start = Offset(x = canvasWidth, y = 0f),
                    end = Offset(x = 0f, y = canvasHeight),
                    color = color,
                    strokeWidth = 9F
                )
            }*/
        }
    }

}

@Composable
fun BuildCard2(modifier: Modifier = Modifier, resources:Resources) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {


        val triangleShape = GenericShape { size, _ ->
            moveTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
        }

        Box(
            modifier = Modifier
                .fillMaxHeight(1f)
                .fillMaxWidth(1f)
                .shadow(10.dp, shape = RoundedCornerShape(CornerSize(10.dp)))
                .background(
                    color = colorResource(id = R.color.teal_200),
                    shape = RoundedCornerShape(CornerSize(10.dp))
                )
                .padding(0.dp)
        ) {
            val colorRgbr = ResourcesCompat.getColor(resources , R.color.teal_200, null)
            val myDarken = ColorUtils2.darkenColor(colorRgbr)
            Surface(
                modifier =
                Modifier.fillMaxSize(),
                elevation = 4.dp,
                shape = triangleShape,
                color = Color(myDarken)
            ){}
        }
    }

}
