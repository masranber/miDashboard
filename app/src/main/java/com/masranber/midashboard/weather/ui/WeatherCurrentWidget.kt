package com.masranber.midashboard.weather.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.node.modifierElementOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.masranber.midashboard.CardHeader
import com.masranber.midashboard.R
import com.masranber.midashboard.news.ui.NewsArticle
import com.masranber.midashboard.ui.theme.*
import com.masranber.midashboard.util.units.fahrenheit
import com.masranber.midashboard.util.units.mph
import com.masranber.midashboard.weather.domain.WeatherAlert
import kotlinx.coroutines.launch
import me.nikhilchaudhari.quarks.CreateParticles
import me.nikhilchaudhari.quarks.particle.*
import kotlin.math.roundToInt

@Composable
fun WeatherAlertContent(alert: WeatherAlert) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .height(36.dp)
                .aspectRatio(1f),
            model = ImageRequest.Builder(LocalContext.current)
                .data(when(alert.severity) {
                    WeatherAlert.Severity.WARNING -> R.drawable.code_red
                    WeatherAlert.Severity.WATCH -> R.drawable.code_orange
                    WeatherAlert.Severity.ADVISORY -> R.drawable.code_orange
                })
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Inside
        )
        //Spacer(modifier = Modifier.width(8.dp))
        Column() {
            Text(
                modifier = Modifier,
                text = alert.what,
                style = Typography.headlineSmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }

    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun WeatherAlertCard(modifier: Modifier = Modifier, alertsViewModel: WeatherAlertsViewModel = viewModel()) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            val state = alertsViewModel.state.collectAsState()

            /*Row(modifier = Modifier.wrapContentHeight()) {
                Text(text = "Alerts", color = ColorPrimaryText)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "| ${state.value.currentAlert + 1} of ${state.value.alerts.size}",
                    color = ColorSecondaryText
                )
            }
            Spacer(modifier = Modifier.height(4.dp))*/

            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()

            coroutineScope.launch {
                pagerState.animateScrollToPage(state.value.currentAlert)
            }

            VerticalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                count = state.value.alerts.size,
                contentPadding = PaddingValues(0.dp),
                state = pagerState,
                userScrollEnabled = false,
                itemSpacing = 0.dp
            ) { page ->
                WeatherAlertContent(alert = state.value.alerts[page])
            }
        }
    }
}

@Composable
fun WeatherSingleDataCard(modifier: Modifier = Modifier, @RawRes icon: Int, name: String, value: String) {
    Box(modifier = modifier
        .background(
            color = ElevatedFrameBgColor,
            shape = RoundedCornerShape(10.dp)
        )
        .border(width = 1.dp, color = FrameOutlineColor, shape = RoundedCornerShape(10.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(icon))
            LottieAnimation(
                modifier = Modifier
                    .size(40.dp),
                composition = composition,
                iterations = LottieConstants.IterateForever,
                contentScale = ContentScale.FillBounds,
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = value,
                    style = Typography.headlineSmall,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = name,
                    style = Typography.titleSmall,
                )
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun WeatherCurrentWidget(weatherViewModel: WeatherCurrentViewModel = viewModel()) {
    var size by remember { mutableStateOf(Size.Zero) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                size = it.size.toSize()
            },
    ) {
        val (Header, WeatherIcon, Temp, Condition, ExtraData, Alert) = createRefs()

        //val chain = createHorizontalChain(WeatherIcon, Temp, chainStyle = ChainStyle.Packed)
        val centerGuideline = createGuidelineFromStart(fraction = 0.5f)

        val state = weatherViewModel.state.collectAsState()

        CardHeader(modifier = Modifier
            .constrainAs(Header) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            },
            title = "Now",
            subtitle = state.value.location
        )

        state.value.weather?.let {

            /*CreateParticles(
                modifier = Modifier.fillMaxSize(),
                x = (size.width/2.0).toFloat(),
                y = -200f,
                velocity = Velocity(xDirection = 1f, yDirection = 0.1f),
                force = Force.Gravity(0.001f),
                acceleration = Acceleration(0f, 0f),
                particleSize = ParticleSize.RandomSizes(2..15),
                particleColor = ParticleColor.SingleColor(Color.White),
                lifeTime = LifeTime(350f, 0.2f),
                emissionType = EmissionType.FlowEmission(maxParticlesCount = EmissionType.FlowEmission.INDEFINITE, 1f),
            )*/

            state.value.weatherIcon?.let {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(it))
                LottieAnimation(
                    modifier = Modifier
                        .constrainAs(WeatherIcon) {
                            top.linkTo(Temp.top)
                            bottom.linkTo(Temp.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(centerGuideline)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        }
                        .aspectRatio(1f),
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    contentScale = ContentScale.FillBounds,
                )
            }

            Column(
                modifier = Modifier
                    .constrainAs(Temp) {
                        top.linkTo(Header.bottom, margin = 0.dp)
                        start.linkTo(centerGuideline)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier,
                    text = "${(it.temp to fahrenheit).value.roundToInt()}°",
                    style = Typography.displayLarge
                )
                Text(
                    modifier = Modifier,
                    text = "${(it.feelsLike to fahrenheit).value.roundToInt()}°",
                    style = Typography.displayMedium
                )
            }

            Text(
                modifier = Modifier
                    .constrainAs(Condition) {
                        top.linkTo(Temp.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                    },
                text = it.conditions[0].desc,
                style = Typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.constrainAs(ExtraData) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(Condition.bottom)
                    bottom.linkTo(Alert.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
            ) {
                WeatherSingleDataCard(modifier = Modifier.weight(1f), icon = R.raw.wind, name = "mph", value = "${((it.windSpeed to mph).value).roundToInt()}")
                WeatherSingleDataCard(modifier = Modifier.weight(1f), icon = R.raw.humidity, name = "%", value = "${(it.humidity * 100).roundToInt()}")
                WeatherSingleDataCard(modifier = Modifier.weight(1f), icon = R.raw.uv_index, name = "UVI", value = "${it.uvIndex.roundToInt()}")
            }
        }

        WeatherAlertCard(modifier = Modifier
            .constrainAs(Alert) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
            .background(color = ElevatedFrameBgColor, shape = RoundedCornerShape(10.dp))
            .border(width = 1.dp, color = FrameOutlineColor, shape = RoundedCornerShape(10.dp))
        )
    }
}