package com.masranber.midashboard.location

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.*
import com.masranber.midashboard.domain.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.time.Duration

enum class LocationError {
    LocationNotAvailable,
    LocationPermissionDenied,
}

class LocationProvider(private val client: FusedLocationProviderClient) {

    constructor(context: Context) : this(LocationServices.getFusedLocationProviderClient(context))

    suspend fun getDeviceLocation(priority: @Priority Int) : Resource<Location, LocationError> {
        try {
            val location: Location? = client.getCurrentLocation(priority, null).await()
            return location?.let { Resource.Success(it) } ?: Resource.Error(LocationError.LocationNotAvailable)
        } catch (e: SecurityException) {
            return Resource.Error(LocationError.LocationPermissionDenied)
        }
    }

    suspend fun getDeviceLastLocation() : Resource<Location, LocationError> {
        try {
            val location: Location? = client.lastLocation.await()
            return location?.let { Resource.Success(it) } ?: Resource.Error(LocationError.LocationNotAvailable)
        } catch (e: SecurityException) {
            return Resource.Error(LocationError.LocationPermissionDenied)
        }
    }

    @RequiresPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun getDeviceLocation(priority: @Priority Int, interval: Duration) = callbackFlow {
        val locationRequest = LocationRequest.Builder(priority, interval.toMillis())
            .setMinUpdateDistanceMeters(1000f)
            .setMinUpdateIntervalMillis(interval.toMillis())
            .build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                trySend(location)
            }
        }

        client.requestLocationUpdates(locationRequest, callback, Looper.getMainLooper())
        awaitClose { client.removeLocationUpdates(callback) }
    }

}