package com.masranber.midashboard.datetime

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime
import java.time.ZonedDateTime

class DateTimeViewModel : ViewModel() {
    private val _dateTime = MutableStateFlow<ZonedDateTime>(ZonedDateTime.now())
    val dateTime: StateFlow<ZonedDateTime> = _dateTime

    fun updateDateTime(dateTime: ZonedDateTime) {
        _dateTime.value = dateTime
    }
}