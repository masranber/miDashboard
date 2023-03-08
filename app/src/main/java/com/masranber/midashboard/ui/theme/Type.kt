package com.masranber.midashboard.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masranber.midashboard.R

val QuicksandFontFamily = FontFamily(
    Font(R.font.quicksand_light,FontWeight.ExtraLight),
    Font(R.font.quicksand_light,FontWeight.Light),
    Font(R.font.quicksand,FontWeight.Normal),
    Font(R.font.quicksand_medium,FontWeight.Medium),
    Font(R.font.quicksand_medium,FontWeight.SemiBold),
    Font(R.font.quicksand_bold,FontWeight.Bold),
    Font(R.font.quicksand_bold,FontWeight.ExtraBold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleMedium = Typography().titleMedium.copy(
        color = ColorPrimaryText,
        fontFamily = QuicksandFontFamily,
        fontSize = 20.sp,
        lineHeight = 20.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    titleSmall = Typography().titleSmall.copy(
        color = ColorSecondaryText,
        fontFamily = QuicksandFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = Typography().displayLarge.copy(
        color = ColorPrimaryText,
        fontFamily = QuicksandFontFamily,
        fontSize = 82.sp,
        fontWeight = FontWeight.SemiBold,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        shadow = Shadow(color = ColorTextShadow, offset = Offset(4.dp.value, 4.dp.value), blurRadius = 10f)
    ),
    displayMedium = Typography().displayMedium.copy(
        color = ColorSecondaryText,
        fontFamily = QuicksandFontFamily,
        fontSize = 48.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false),
        shadow = Shadow(color = ColorTextShadow, offset = Offset(4.dp.value, 4.dp.value), blurRadius = 10f)
    ),
    headlineLarge = Typography().headlineLarge.copy(
        color = ColorPrimaryText,
        fontFamily = QuicksandFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    headlineSmall = Typography().headlineSmall.copy(
        color = ColorPrimaryText,
        fontFamily = QuicksandFontFamily,
        fontSize = 24.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)