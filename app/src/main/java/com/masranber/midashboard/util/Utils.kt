package com.masranber.midashboard.util


fun String.truncate(maxLength: Int, ellipses: Boolean = true) : String {
    if(this.length > maxLength) {
        val trunc = this.take(maxLength)
        if(ellipses) {
            return trunc + "..."
        } else {
            return trunc
        }
    } else {
        return this
    }
}