package com.masranber.midashboard.util.objectbox

import android.content.Context
import com.masranber.midashboard.lists.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {
    private lateinit var boxStore: BoxStore

    fun init(applicationContext: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(applicationContext)
            .build()
    }

    fun get() : BoxStore {
        return boxStore
    }
}