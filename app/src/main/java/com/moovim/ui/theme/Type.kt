package com.moovim.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.moovim.R

private val Inter= FontFamily(
        Font(R.font.inter_light, FontWeight.W300),
        Font(R.font.inter_regular, FontWeight.W400),
        Font(R.font.inter_medium, FontWeight.W500),
        Font(R.font.inter_bold, FontWeight.W600),
)

// Set of Material typography styles to start with
val Typography = Typography(
        h1 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W500,
                fontSize = 30.sp
        ),
        h2 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W500,
                fontSize = 24.sp)
        ,
        h3 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W500,
                fontSize = 20.sp),
        h4 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W400,
                fontSize = 18.sp),
        h5 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp),
        h6 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp),
        body1 = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp)


        /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)