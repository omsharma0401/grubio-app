package com.omsharma.grubio.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.omsharma.grubio.R

val metropolisFontFamily = FontFamily(
    Font(R.font.metropolis_regular),
    Font(R.font.metropolis_medium, FontWeight.Medium),
    Font(R.font.metropolis_semibold, FontWeight.SemiBold),
    Font(R.font.metropolis_bold, FontWeight.Bold),
    Font(R.font.metropolis_thin, FontWeight.Thin),

)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = metropolisFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)