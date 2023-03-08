package com.masranber.midashboard.datetime

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Typeface
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masranber.midashboard.CardHeader
import com.masranber.midashboard.ui.theme.ColorPrimaryText
import com.masranber.midashboard.ui.theme.ColorSecondaryText
import com.masranber.midashboard.ui.theme.Typography
import java.time.format.DateTimeFormatter


@Composable
fun DateTimeWidget(viewModel: DateTimeViewModel, modifier: Modifier = Modifier, title: String = "It's") {
    Box(modifier = modifier) {
        val blinkAnim = rememberInfiniteTransition()
        val alpha by blinkAnim.animateFloat(
            initialValue = 1f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 500
                    1f at 0
                    1f at 249
                    0f at 251
                    0f at 500
                },
                repeatMode = RepeatMode.Reverse
            )
        )

        CardHeader(title = "It's")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(0.dp, 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val dateTime by viewModel.dateTime.collectAsState()
            Row() {
                Text(
                    text = dateTime.format(DateTimeFormatter.ofPattern("h")),
                    style = Typography.displayLarge,
                    modifier = Modifier.alignByBaseline(),
                )
                Text(
                    text = ":",
                    style = Typography.displayLarge,
                    modifier = Modifier
                        .alpha(alpha)
                        .alignByBaseline(),
                )
                Text(
                    text = dateTime.format(DateTimeFormatter.ofPattern("mm")),
                    style = Typography.displayLarge,
                    modifier = Modifier.alignByBaseline()
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = dateTime.format(DateTimeFormatter.ofPattern("a")),
                    style = Typography.displayMedium,
                    color = ColorPrimaryText,
                    modifier = Modifier.alignByBaseline()
                )
            }
            Text(
                text = dateTime.format(DateTimeFormatter.ofPattern("E LLL d")),
                style = Typography.displayMedium,
            )
        }
    }
}