package com.masranber.midashboard

import android.Manifest.permission.*
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.masranber.midashboard.calendar.CalendarCard
import com.masranber.midashboard.datetime.DateTimeBroadcastReceiver
import com.masranber.midashboard.datetime.DateTimeViewModel
import com.masranber.midashboard.datetime.DateTimeWidget
import com.masranber.midashboard.lists.todo.TodoListWidget
import com.masranber.midashboard.music.ui.NowPlayingCard
import com.masranber.midashboard.news.ui.NewsWidget
import com.masranber.midashboard.ui.theme.*
import com.masranber.midashboard.util.blur.CustomCapturable
import com.masranber.midashboard.weather.ui.WeatherCurrentWidget
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {

    private var dateTimeBroadcastReceiver: DateTimeBroadcastReceiver? = null
    private val dateTimeViewModel: DateTimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestPermissions()
        setContent {
            MiDashboardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Sample(dateTimeViewModel)
                }
            }
        }

        dateTimeBroadcastReceiver ?: run {
            dateTimeBroadcastReceiver = DateTimeBroadcastReceiver(dateTimeViewModel)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dateTimeViewModel.dateTime.collect {
                    Log.i("MainActivity", "Date/Time Updated: $it")
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()
        dateTimeBroadcastReceiver?.register(this)
    }

    override fun onPause() {
        super.onPause()
        dateTimeBroadcastReceiver?.unregister(this)
    }

    /*
     * Runtime permissions
     */
    private val REQUIRED_PERMISSION_LIST = arrayOf(
        INTERNET,
        ACCESS_NETWORK_STATE,
        ACCESS_COARSE_LOCATION,
        SYSTEM_ALERT_WINDOW
    )
    private val REQUEST_PERMISSION_CODE = 12345
    private val missingPermission: MutableList<String> = ArrayList()

    /**
     * Requests missing app permissions from the user
     */
    fun checkAndRequestPermissions() {
        // Check for permissions
        for (eachPermission in REQUIRED_PERMISSION_LIST) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    eachPermission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                missingPermission.add(eachPermission)
            }
        }
        // Request for missing permissions
        if (!missingPermission.isEmpty()) {
            requestPermissions(
                this,
                missingPermission.toTypedArray(),
                REQUEST_PERMISSION_CODE
            )
        }
    }

    /**
     * Result of runtime permission request
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Check for granted permission and remove from missing list
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (i in grantResults.indices.reversed()) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    missingPermission.remove(permissions[i])
                }
            }
        }
        // Show UI toast notification if permissions are still missing
        if (!missingPermission.isEmpty()) {
        }
    }
}


@Composable
fun BackgroundWallpaper(modifier: Modifier = Modifier, @DrawableRes wallpaperRes: Int) {
    /*AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(wallpaperRes)
            .allowHardware(false)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize(),
    )*/
    Image(
        painter = painterResource(id = wallpaperRes),
        contentDescription = "",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxSize(),
    )
}


data class Place(var size: IntSize = IntSize.Zero, var offset: Offset = Offset.Zero)

@Composable
fun GlassmorphicColumn(
    modifier: Modifier = Modifier,
    blurredBitmap: Bitmap?,
    cornerRadius: Dp = 0f.dp,
    content: @Composable() () -> Unit,
) {
    var containerMeasures by remember { mutableStateOf(Place()) }

    Surface(
        modifier = modifier.onGloballyPositioned {
            containerMeasures = Place(
                it.size,
                it.positionInRoot()
            )
        },
        shape = RoundedCornerShape(cornerRadius),
        color = Color.Transparent,
        shadowElevation = 4.dp,
    ) {
        androidx.compose.foundation.Canvas(
            modifier = Modifier
        )
        {
            val path = Path()
            path.addRoundRect(
                RoundRect(
                    Rect(
                        offset = Offset.Zero,//-containerMeasures.offset,
                        size = Size(containerMeasures.size.width.toFloat(), containerMeasures.size.height.toFloat())
                    ),
                    CornerRadius(cornerRadius.toPx())
                )
            )

            clipPath(path) {
                blurredBitmap?.let {
                    drawImage(
                        it.asImageBitmap(),
                        -containerMeasures.offset
                    )
                }
            }
        }
        content()
    }
}


@Composable
fun DraggableBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    content: @Composable() (BoxScope.() -> Unit)
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
        content = content
    )
}

@Stable
data class BitmapHolder(var bitmap: Bitmap?)

@Composable
fun Sample(dateTimeViewModel: DateTimeViewModel) {
    val context = LocalContext.current
    var count by remember { mutableStateOf(0) }
    val renderScriptBlur = remember {
        com.masranber.midashboard.RenderScriptBlur(context)
    }
    var blurredBitmap: BitmapHolder by remember { mutableStateOf(BitmapHolder(null)) }
    val background by remember { mutableStateOf(R.drawable.wp_switzerland_village) }

    CustomCapturable(
        activity = LocalContext.current as Activity,
        onCaptured = {
            val downscaled = Bitmap.createScaledBitmap(it, (it.width / 4f).toInt(), (it.height / 4f).toInt(), false)
            val start = System.nanoTime()
            val blurred = renderScriptBlur.blur(downscaled, 10)
            Log.i("BlurProfiler", "step [blur] took ${(System.nanoTime() - start) / 1_000_000.0} ms")
            val upscaled = Bitmap.createScaledBitmap(blurred, it.width, it.height, false)
            //blurredBitmap.bitmap?.recycle()
            blurredBitmap = BitmapHolder(upscaled)
            count++
        }
    ) {
        Image(
            painter = painterResource(background),
            contentDescription = "Circle Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }

    Text(
        text = "DEBUG: global blur count = $count",
        color = ColorPrimaryText,
        modifier = Modifier.offset(10.dp, 0.dp)
    )

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)
    ) {
        val (dateTime, weather, alerts, music, calendar, list, news) = createRefs()

        Box(modifier = Modifier
            .constrainAs(dateTime) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            },
        ) {
            GlassmorphicColumn(
                modifier = Modifier,
                blurredBitmap = blurredBitmap.bitmap,
                cornerRadius = 20.dp,
                content = {
                    DateTimeWidget(
                        viewModel = dateTimeViewModel,
                        modifier = Modifier
                            .background(FrameBgColor)
                            .border(1.dp, ElevatedFrameBgColor, shape = RoundedCornerShape(20.dp))
                            .fillMaxWidth(0.2f)
                            .fillMaxHeight(0.2f)
                            .padding(10.dp)
                    )
                },
            )
        }

        Box(modifier = Modifier
            .constrainAs(weather) {
                start.linkTo(parent.start)
                top.linkTo(dateTime.bottom, margin = 20.dp)
                bottom.linkTo(music.top, margin = 20.dp)
                height = Dimension.fillToConstraints
            },
        ) {
            GlassmorphicColumn(
                modifier = Modifier,
                blurredBitmap = blurredBitmap.bitmap,
                cornerRadius = 20.dp,
                content = {
                    Box(modifier = Modifier
                        .background(FrameBgColor)
                        .border(1.dp, ElevatedFrameBgColor, shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth(0.2f)
                        .padding(10.dp)
                    ) {
                        WeatherCurrentWidget()
                    }
                },
            )
        }

        Box(modifier = Modifier
            .constrainAs(music) {
                start.linkTo(parent.start)
                linkTo(music.bottom, parent.bottom, 0.dp, 0.dp, 0.dp, 0.dp, 1f)
                //bottom.linkTo(parent.bottom)
            },
        ) {
            GlassmorphicColumn(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .aspectRatio(1.0f),
                blurredBitmap = blurredBitmap.bitmap,
                cornerRadius = 20.dp,
                content = {
                    NowPlayingCard()
                },
            )
        }

        GlassmorphicColumn(
            modifier = Modifier
                .constrainAs(list) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(news.top, margin = 20.dp)
                    height = Dimension.fillToConstraints
                },
            blurredBitmap = blurredBitmap.bitmap,
            cornerRadius = 20.dp,
            content = {
                Box(
                    modifier = Modifier
                        .background(FrameBgColor)
                        .border(1.dp, ElevatedFrameBgColor, shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth(0.2f)
                        .padding(10.dp)
                ) {
                    TodoListWidget()
                }
            },
        )


        Box(modifier = Modifier
            .constrainAs(news) {
                start.linkTo(music.end, margin = 20.dp)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            },
        ) {
            GlassmorphicColumn(
                modifier = Modifier,
                blurredBitmap = blurredBitmap.bitmap,
                cornerRadius = 20.dp,
                content = {
                    Box(
                        modifier = Modifier
                            .background(FrameBgColor)
                            .border(1.dp, ElevatedFrameBgColor, shape = RoundedCornerShape(20.dp))
                            .fillMaxHeight(0.2f)
                            .padding(10.dp)
                    ) {
                        NewsWidget()
                    }
                },
            )
        }

        Box(modifier = Modifier
            .constrainAs(calendar) {
                start.linkTo(dateTime.end, margin = 20.dp)
                end.linkTo(list.start, margin = 20.dp)
                top.linkTo(parent.top)
                bottom.linkTo(news.top, margin = 20.dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
        ) {
            GlassmorphicColumn(
                modifier = Modifier,
                blurredBitmap = blurredBitmap.bitmap,
                cornerRadius = 20.dp,
                content = {
                    Box(modifier = Modifier
                        .background(FrameBgColor)
                        .border(1.dp, ElevatedFrameBgColor, shape = RoundedCornerShape(20.dp))
                        .fillMaxSize()
                        .padding(10.dp)
                    ) {
                        CalendarCard()
                    }
                },
            )
        }

        /*Button(
            modifier = Modifier
                .offset(100.dp, 100.dp),
            onClick = {
                background = when(background) {
                    R.drawable.background_winter -> R.drawable.background_winter2
                    R.drawable.background_winter2 -> R.drawable.background_winter
                    else -> R.drawable.background_winter
                }
            }
        ) {
            Text(text = "Next Background")
        }*/
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MiDashboardTheme {
        BackgroundWallpaper(wallpaperRes = R.drawable.background_winter)
    }
}