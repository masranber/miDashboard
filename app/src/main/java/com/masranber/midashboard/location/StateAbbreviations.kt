package com.masranber.midashboard.location

fun mapStateToAbbreviation(state: String) = when(state) {
    "Wisconsin" -> "WI"
    else -> state
}