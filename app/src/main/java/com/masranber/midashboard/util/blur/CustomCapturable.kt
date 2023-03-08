package com.masranber.midashboard.util.blur

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.applyCanvas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class CustomComposeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    init {
        setWillNotDraw(false)
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    private val content = mutableStateOf<(@Composable () -> Unit)?>(null)

    var onBitmapCaptured: ((Bitmap) -> Unit)? = null
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    var lastCapture: Long = 0

    @Suppress("RedundantVisibilityModifier")
    protected override var shouldCreateCompositionOnAttachedToWindow: Boolean = false
        private set

    @Composable
    override fun Content() {
        Log.i("CustomComposeView", "Content()")
        content.value?.invoke()
    }

    override fun getAccessibilityClassName(): CharSequence {
        return javaClass.name
    }

    /**
     * Set the Jetpack Compose UI content for this view.
     * Initial composition will occur when the view becomes attached to a window or when
     * [createComposition] is called, whichever comes first.
     */
    fun setContent(content: @Composable () -> Unit) {
        shouldCreateCompositionOnAttachedToWindow = true
        this.content.value = content
        if (isAttachedToWindow) {
            createComposition()
        }
    }

    override fun draw(canvas: Canvas?) {
        //val start = System.nanoTime()
        super.draw(canvas)
        if((System.nanoTime() - lastCapture) < 10_000_000) return;
        //Log.i("BlurProfiler", "step [draw] took ${(System.nanoTime() - start) / 1_000_000.0} ms")
        Log.i("CustomComposeView", "onDraw()")
        onBitmapCaptured?.let {
            val start = System.nanoTime()
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).applyCanvas {
                translate(-scrollX.toFloat(), -scrollY.toFloat())
                super.draw(this)
            }
            lastCapture = System.nanoTime()
            Log.i("BlurProfiler", "step [draw] took ${(System.nanoTime() - start) / 1_000_000.0} ms")
            it(bitmap)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.i("CustomComposeView", "onDraw()")
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        Log.i("CustomComposeView", "dispatchDraw()")
    }
}

@Composable
fun CustomCapturable(
    modifier: Modifier = Modifier,
    activity: Activity,
    onCaptured: (Bitmap) -> Unit,
    content: @Composable () -> Unit
) {
    /*val context = LocalContext.current
    val renderScriptBlur = remember { RenderScriptBlur(context) }
    var blurredBitmap: Bitmap? by remember { mutableStateOf(null) }

    SideEffect {
        Log.i("CustomCapturable", "SideEffect")
    }*/

    AndroidView(
        factory = { CustomComposeView(activity).apply {
            setContent {
                content()
            }
            onBitmapCaptured = onCaptured
        } },
        modifier = modifier
    )
}

/*
/**
 * Sets the [content] in [ComposeView] and handles the capture of a [content].
 */
private inline fun ComposeView.applyCapturability(
    noinline onCaptured: (BitmapHolder) -> Unit,
    crossinline content: @Composable () -> Unit,
    context: Context
) = apply {
    setContent {
        content()
        /*LaunchedEffect(true) {
            try {
                Log.i("Capturable", "capture hierarchy")
                val bitmap = drawToBitmapPostLaidOut(context, Bitmap.Config.ARGB_8888)
                onCaptured(bitmap.asImageBitmap(), null)
            } catch (t: Throwable) {
                onCaptured(null, t)
            }
        }*/
    }

    setOnClickListener { onCaptured(BitmapHolder(null)) }

}


private suspend fun View.drawToBitmapPostLaidOut(context: Context, config: Bitmap.Config): Bitmap {
    return suspendCoroutine { continuation ->
        continuation.resume(drawToBitmap(config))
        /*val window = context.findActivity().window

        drawBitmapWithPixelCopy(
            view = this,
            window = window,
            config = config,
            onDrawn = { bitmap -> continuation.resume(bitmap) },
            onError = { error -> continuation.resumeWithException(error) }
        )*/
        //doOnLayout { view ->
//            try {
//                // Initially, try to capture bitmap using drawToBitmap extension function
//                continuation.resume(view.drawToBitmap(config))
//            } catch (e: IllegalArgumentException) {
//                // For device with API version O(26) and above should draw Bitmap using PixelCopy
//                // API. The reason behind this is it throws IllegalArgumentException saying
//                // "Software rendering doesn't support hardware bitmaps"
//                // See this issue for the reference:
//                // https://github.com/PatilShreyas/Capturable/issues/7
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    val window = context.findActivity().window
//
//                    drawBitmapWithPixelCopy(
//                        view = view,
//                        window = window,
//                        config = config,
//                        onDrawn = { bitmap -> continuation.resume(bitmap) },
//                        onError = { error -> continuation.resumeWithException(error) }
//                    )
//                } else {
//                    continuation.resumeWithException(e)
//                }
//            }
        //}
    }
}


@RequiresApi(Build.VERSION_CODES.O)
private fun drawBitmapWithPixelCopy(
    window: Window,
    view: View,
    config: Bitmap.Config,
    onDrawn: (Bitmap) -> Unit,
    onError: (Throwable) -> Unit
) {
    val width = view.width
    val height = view.height

    val bitmap = Bitmap.createBitmap(width, height, config)

    val (x, y) = IntArray(2).apply { view.getLocationInWindow(this) }
    val rect = Rect(x, y, x + width, y + height)

    PixelCopy.request(
        window,
        rect,
        bitmap,
        { copyResult ->
            if (copyResult == PixelCopy.SUCCESS) {
                onDrawn(bitmap)
            } else {
                onError(RuntimeException("Failed to draw bitmap"))
            }
        },
        Handler(Looper.getMainLooper())
    )
}


internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Unable to retrieve Activity from the current context")
}
*/