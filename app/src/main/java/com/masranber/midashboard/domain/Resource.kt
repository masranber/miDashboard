package com.masranber.midashboard.domain

sealed class Resource<T, E>(val data: T? = null, val error: E? = null) {
    class Success<T, E>(data: T?): Resource<T, E>(data)
    class Error<T, E>(error: E?, data: T? = null): Resource<T, E>(data, error)
}