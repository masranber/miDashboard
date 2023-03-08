package com.masranber.midashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class StatefulViewModel<T> : ViewModel() {

    protected val mutableState: MutableStateFlow<T> by lazy { MutableStateFlow(getInitialState()) }
    val state: StateFlow<T> = mutableState

    protected abstract fun getInitialState() : T
}