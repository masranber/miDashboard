package com.masranber.midashboard.weather.domain

import com.masranber.midashboard.util.units.*
import com.masranber.midashboard.weather.data.openweatherapi.*
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 *From 'https://openweathermap.org/weather-conditions'
 */
fun OpenWeatherConditionDTO.toWeatherCondition() = when(this.id) {
    // Group 2xx: Thunderstorm
    200 -> WeatherCondition.ThunderstormLightRain
    201 -> WeatherCondition.ThunderstormRain
    202 -> WeatherCondition.ThunderstormHeavyRain
    210 -> WeatherCondition.ThunderstormLight
    211 -> WeatherCondition.Thunderstorm
    212 -> WeatherCondition.ThunderstormHeavy
    221 -> WeatherCondition.ThunderstormScattered
    230 -> WeatherCondition.ThunderstormLightDrizzle
    231 -> WeatherCondition.ThunderstormDrizzle
    232 -> WeatherCondition.ThunderstormHeavyDrizzle
    // Group 3xx: Drizzle
    300 -> WeatherCondition.DrizzleLight
    301 -> WeatherCondition.Drizzle
    302 -> WeatherCondition.DrizzleHeavy
    310 -> WeatherCondition.DrizzleLight
    311 -> WeatherCondition.Drizzle
    312 -> WeatherCondition.DrizzleHeavy
    313 -> WeatherCondition.Drizzle
    314 -> WeatherCondition.DrizzleHeavy
    321 -> WeatherCondition.Drizzle
    // Group 5xx: Rain
    500 -> WeatherCondition.RainLight
    501 -> WeatherCondition.Rain
    502 -> WeatherCondition.RainHeavy
    503 -> WeatherCondition.RainHeavy
    504 -> WeatherCondition.RainHeavy
    511 -> WeatherCondition.RainFreezing
    520 -> WeatherCondition.RainShowerLight
    521 -> WeatherCondition.RainShower
    522 -> WeatherCondition.RainShowerHeavy
    531 -> WeatherCondition.RainShowerScattered
    // Group 6xx: Snow
    600 -> WeatherCondition.SnowLight
    601 -> WeatherCondition.Snow
    602 -> WeatherCondition.SnowHeavy
    611 -> WeatherCondition.SnowSleet
    612 -> WeatherCondition.SnowSleet
    613 -> WeatherCondition.SnowSleet
    615 -> WeatherCondition.SnowLightRain
    616 -> WeatherCondition.SnowRain
    620 -> WeatherCondition.SnowLightShower
    621 -> WeatherCondition.SnowShower
    622 -> WeatherCondition.SnowHeavyShower
    // Group 7xx: Atmosphere
    701 -> WeatherCondition.AtmosphericMist
    711 -> WeatherCondition.AtmosphericSmoke
    721 -> WeatherCondition.AtmosphericHaze
    731 -> WeatherCondition.AtmosphericDust
    741 -> WeatherCondition.AtmosphericFog
    751 -> WeatherCondition.AtmosphericSand
    761 -> WeatherCondition.AtmosphericDust
    762 -> WeatherCondition.AtmosphericVolcanicAsh
    771 -> WeatherCondition.AtmosphericSquall
    781 -> WeatherCondition.AtmosphericTornado
    // Group 800: Clear
    800 -> WeatherCondition.Clear
    // Group 80x: Clouds
    801 -> WeatherCondition.CloudyPartly
    802 -> WeatherCondition.CloudyScattered
    803 -> WeatherCondition.CloudyMostly
    804 -> WeatherCondition.CloudyOvercast
    // Fallback
    else -> WeatherCondition.Clear
}

private fun getWeatherAlertSeverity(event: String) : WeatherAlert.Severity {
    return with(event) {
        when {
            contains("warning", ignoreCase = true) -> WeatherAlert.Severity.WARNING
            contains("watch", ignoreCase = true) -> WeatherAlert.Severity.WATCH
            contains("advisory", ignoreCase = true) -> WeatherAlert.Severity.ADVISORY
            else -> WeatherAlert.Severity.ADVISORY
        }
    }
}

fun OpenWeatherAlertDTO.toWeatherAlert() = WeatherAlert(
    source = this.senderName,
    severity = getWeatherAlertSeverity(this.event),
    what = this.event,
    from = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this.start), ZoneId.systemDefault()),
    until = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this.end), ZoneId.systemDefault()),
    detailed = this.description,
    tags = this.tags,
)

fun OpenWeatherDailyTempDTO.toTemperatureForecastDaily() = TemperatureForecastDaily(
    morning = this.morning.celsius,
    day = this.day.celsius,
    evening = this.evening.celsius,
    night = this.night.celsius,
    min = this.min?.celsius,
    max = this.max?.celsius
)

fun OpenWeatherCurrentDTO.toWeatherCurrent(tempForecast: OpenWeatherDailyTempDTO, alerts: List<OpenWeatherAlertDTO>) = WeatherCurrent(
    time = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this.time), ZoneId.systemDefault()),
    sunriseTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this.sunrise), ZoneId.systemDefault()),
    sunsetTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this.sunset), ZoneId.systemDefault()),
    temp = this.temp.celsius,
    feelsLike = this.feelsLike.celsius,
    tempForecast = tempForecast.toTemperatureForecastDaily(),
    pressure = this.pressure,
    humidity = this.humidity / 100f,
    dewPoint = this.dewPoint.celsius,
    uvIndex = this.uvIndex,
    cloudCover = this.clouds / 100f,
    visibility = this.visibility.meters,
    windSpeed = this.windSpeed.m_s,
    windDirection = this.windDirection,
    windGustSpeed = if(this.windGust != null) this.windGust.m_s else this.windSpeed.m_s,
    conditions = this.conditions.map { it.toWeatherCondition() },
    alerts = alerts.map { it.toWeatherAlert() }.sortedByDescending { it.severity }, // highest severity first
)

fun OpenWeatherDailyDTO.toWeatherForecastDaily() = WeatherForecastDaily(
    time = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this.time), ZoneId.systemDefault()),
    sunriseTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this.sunrise), ZoneId.systemDefault()),
    sunsetTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this.sunset), ZoneId.systemDefault()),
    moonriseTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this.moonrise), ZoneId.systemDefault()),
    moonsetTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this.moonset), ZoneId.systemDefault()),
    moonPhase = this.moonPhase,
    temp = this.temp.toTemperatureForecastDaily(),
    feelsLike = this.feelsLike.toTemperatureForecastDaily(),
    pressure = this.pressure,
    humidity = this.humidity / 100f,
    dewPoint = this.dewPoint.celsius,
    uvIndex = this.uvIndex,
    cloudCover = this.clouds / 100f,
    windSpeed = this.windSpeed.m_s,
    windDirection = this.windDirection,
    windGustSpeed = if(this.windGust != null) this.windGust.m_s else this.windSpeed.m_s,
    precipitationChance = this.pop,
    rain = (if(this.rain != null) this.rain else 0).milli.meters,
    snow = (if(this.snow != null) this.snow else 0).milli.meters,
    conditions = this.conditions.map { it.toWeatherCondition() }
)

fun OpenWeatherPrecipitation1hDTO.toLength() : Length {
    return this.nextHour.milli.meters
}

fun OpenWeatherHourlyDTO.toWeatherForecastHourly() = WeatherForecastHourly(
    time = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this.time), ZoneId.systemDefault()),
    temp = this.temp.celsius,
    feelsLike = this.feelsLike.celsius,
    pressure = this.pressure,
    humidity = this.humidity / 100f,
    dewPoint = this.dewPoint.celsius,
    uvIndex = this.uvIndex,
    cloudCover = this.clouds / 100f,
    visibility = this.visibility.meters,
    windSpeed = this.windSpeed.m_s,
    windDirection = this.windDirection,
    windGustSpeed = if(this.windGust != null) this.windGust.m_s else this.windSpeed.m_s,
    precipitationChance = this.pop,
    rain = this.rain?.toLength() ?: 0.milli.meters,
    snow = this.snow?.toLength() ?: 0.milli.meters,
    conditions = this.conditions.map { it.toWeatherCondition() }
)

fun OpenWeatherMinutelyDTO.toWeatherForecastMinutely() = WeatherForecastMinutely(
    time = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this.time), ZoneId.systemDefault()),
    precipitation = this.precipitation.milli.meters
)

fun OpenWeatherDTO.toWeatherForecast() = WeatherForecast(
    lat = this.lat,
    lon = this.lon,
    locationString = null,
    minutely = this.minutely.map { it.toWeatherForecastMinutely() },
    hourly = this.hourly.map { it.toWeatherForecastHourly() },
    daily = this.daily.map { it.toWeatherForecastDaily() },
    current = this.current.toWeatherCurrent(this.daily[0].temp, this.alerts ?: emptyList()),
    alerts = this.alerts?.map { it.toWeatherAlert() } ?: emptyList()
)