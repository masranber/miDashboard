package com.masranber.midashboard

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.annotation.WorkerThread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class RenderScriptBlur(context: Context) {

    private val rs: RenderScript? = RenderScript.create(context)

    fun blur(src: Bitmap, radius: Int): Bitmap {
        rs?.let {
            val input = Allocation.createFromBitmap(it, src)
            val output = Allocation.createTyped(it, input.type)
            val script = ScriptIntrinsicBlur.create(it, Element.U8_4(it))
            script.apply {
                setRadius(radius.toFloat())
                setInput(input)
                forEach(output)
            }
            output.copyTo(src)
        }
        return src
    }

    @WorkerThread
    fun blurBitmap(bitmap: Bitmap, applicationContext: Context, radius: Float = 10f): Bitmap {
        lateinit var rsContext: RenderScript
        try {

            // Create the output bitmap
            val output = Bitmap.createBitmap(
                bitmap.width, bitmap.height, bitmap.config
            )

            // Blur the image
            rsContext = RenderScript.create(applicationContext, RenderScript.ContextType.NORMAL)
            val inAlloc = Allocation.createFromBitmap(rsContext, bitmap)
            val outAlloc = Allocation.createTyped(rsContext, inAlloc.type)
            val theIntrinsic = ScriptIntrinsicBlur.create(rsContext, Element.U8_4(rsContext))
            theIntrinsic.apply {
                setRadius(radius)
                theIntrinsic.setInput(inAlloc)
                theIntrinsic.forEach(outAlloc)
            }
            outAlloc.copyTo(output)

            return output
        } finally {
            rsContext.finish()
        }
    }

    /*fun blur(src: View?, radius: Int): Bitmap? {
        val bitmap: Bitmap = getBitmapForView(src)
        return blur(bitmap, radius)
    }

    fun fastBlur(src: View?, radius: Int, downscaleFactor: Float): Bitmap? {
        val bitmap: Bitmap = getBitmapForView(src, downscaleFactor)
        return blur(bitmap, radius)
    }*/
}