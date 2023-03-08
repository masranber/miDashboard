package com.masranber.midashboard.datetime

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import java.time.LocalDateTime
import java.time.ZonedDateTime


class DateTimeBroadcastReceiver(private val viewModel: DateTimeViewModel?) : BroadcastReceiver() {

    constructor() : this(null)

    private val timeDateIntentFilter: IntentFilter = IntentFilter().apply {
        addAction(Intent.ACTION_TIME_TICK)
        addAction(Intent.ACTION_TIMEZONE_CHANGED)
        addAction(Intent.ACTION_TIME_CHANGED)
        addAction(Intent.ACTION_DATE_CHANGED)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.action?.let {
            if(
                it == Intent.ACTION_TIME_TICK ||
                it == Intent.ACTION_TIMEZONE_CHANGED ||
                it == Intent.ACTION_TIME_CHANGED ||
                it == Intent.ACTION_DATE_CHANGED
            ) {
                Log.i("MainActivity", "Date/Time Broadcast: $it")
                viewModel?.updateDateTime(ZonedDateTime.now())
            }
        }
    }

    fun register(context: Context) {
        context.registerReceiver(this, timeDateIntentFilter)
        Log.i("MainActivity", "Date/Time Broadcast: Registered")

        // covers situation where activity registers after being unregistered for several minutes
        // and view model state is outdated for up to 1 minute until next broadcast is received
        viewModel?.updateDateTime(ZonedDateTime.now())
    }

    fun unregister(context: Context) {
        context.unregisterReceiver(this)
        Log.i("MainActivity", "Date/Time Broadcast: Unregistered")
    }
}