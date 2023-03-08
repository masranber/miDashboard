package com.masranber.midashboard.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.masranber.midashboard.domain.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

enum class GeocodingError{
    AddressNotFound,
}

class GeocodingProvider(context: Context) {
    val geocoder: Geocoder = Geocoder(context)

    suspend fun getAddressForLocation(lat: Double, lon: Double) : Resource<Address, GeocodingError> {
        val address = geocoder.getFromLocation(lat, lon, 1)?.firstOrNull()
        return when(address != null) {
            true -> Resource.Success(address)
            false -> Resource.Error(GeocodingError.AddressNotFound)
        }
    }
}