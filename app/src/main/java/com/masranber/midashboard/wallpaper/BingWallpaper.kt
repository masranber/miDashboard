package com.masranber.midashboard.wallpaper

import com.google.gson.annotations.SerializedName

data class BingWallpaper(
    @SerializedName("start_date")     val startDate:     String?,
    @SerializedName("end_date")       val endDate:       String?,
    @SerializedName("url")            val url:           String?,
    @SerializedName("copyright")      val copyright:     String?,
    @SerializedName("copyright_link") val copyrightLink: String?,
)
