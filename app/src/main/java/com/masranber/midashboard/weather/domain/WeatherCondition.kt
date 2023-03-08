package com.masranber.midashboard.weather.domain

import com.masranber.midashboard.R

sealed class WeatherCondition(
    val desc: String,
    val icon: Any,
    val iconAlt: Any,
) {
    object ThunderstormLightRain : WeatherCondition(
        desc = "Thunderstorm w/ Light Rain",
        icon = R.raw.thunderstorms_rain,
        iconAlt = R.raw.thunderstorms_night_rain,
        //icon = "http://openweathermap.org/img/wn/11d@2x.png",
    )
    object ThunderstormRain : WeatherCondition(
        desc = "Thunderstorm w/ Rain",
        icon = R.raw.thunderstorms_rain,
        iconAlt = R.raw.thunderstorms_night_rain,
        //icon = "http://openweathermap.org/img/wn/11d@2x.png",
    )
    object ThunderstormHeavyRain : WeatherCondition(
        desc = "Thunderstorm w/ Heavy Rain",
        icon = R.raw.thunderstorms_rain,
        iconAlt = R.raw.thunderstorms_night_rain,
        //icon = "http://openweathermap.org/img/wn/11d@2x.png",
    )
    object ThunderstormLight : WeatherCondition(
        desc = "Light Thunderstorm",
        icon = R.raw.thunderstorms_day,
        iconAlt = R.raw.thunderstorms_night,
        //icon = "http://openweathermap.org/img/wn/11d@2x.png",
    )
    object Thunderstorm : WeatherCondition(
        desc = "Thunderstorm",
        icon = R.raw.thunderstorms,
        iconAlt = R.raw.thunderstorms_night,
        //icon = "http://openweathermap.org/img/wn/11d@2x.png",
    )
    object ThunderstormHeavy : WeatherCondition(
        desc = "Heavy Thunderstorm",
        icon = R.raw.thunderstorms_extreme,
        iconAlt = R.raw.thunderstorms_night_extreme,
        //icon = "http://openweathermap.org/img/wn/11d@2x.png",
    )
    object ThunderstormScattered : WeatherCondition(
        desc = "Scattered Thunderstorms",
        icon = R.raw.thunderstorms_day,
        iconAlt = R.raw.thunderstorms_night,
        //icon = "http://openweathermap.org/img/wn/11d@2x.png",
    )
    object ThunderstormLightDrizzle : WeatherCondition(
        desc = "Thunderstorm w/ Light Drizzle",
        icon = R.raw.thunderstorms_rain,
        iconAlt = R.raw.thunderstorms_night_rain,
        //icon = "http://openweathermap.org/img/wn/11d@2x.png",
    )
    object ThunderstormDrizzle : WeatherCondition(
        desc = "Thunderstorm w/ Drizzle",
        icon = R.raw.thunderstorms_rain,
        iconAlt = R.raw.thunderstorms_night_rain,
        //icon = "http://openweathermap.org/img/wn/11d@2x.png",
    )
    object ThunderstormHeavyDrizzle : WeatherCondition(
        desc = "Thunderstorm w/ Heavy Drizzle",
        icon = R.raw.thunderstorms_rain,
        iconAlt = R.raw.thunderstorms_night_rain,
        //icon = "http://openweathermap.org/img/wn/11d@2x.png",
    )
    object DrizzleLight : WeatherCondition(
        desc = "Light Drizzle",
        icon = R.raw.partly_cloudy_day_drizzle,
        iconAlt = R.raw.partly_cloudy_night_drizzle,
        //icon = "http://openweathermap.org/img/wn/09d@2x.png",
    )
    object Drizzle : WeatherCondition(
        desc = "Drizzle",
        icon = R.raw.overcast_day_drizzle,
        iconAlt = R.raw.overcast_night_drizzle,
        //icon = "http://openweathermap.org/img/wn/09d@2x.png",
    )
    object DrizzleHeavy : WeatherCondition(
        desc = "Heavy Drizzle",
        icon = R.raw.extreme_day_drizzle,
        iconAlt = R.raw.extreme_night_drizzle,
        //icon = "http://openweathermap.org/img/wn/09d@2x.png",
    )
    object RainLight : WeatherCondition(
        desc = "Light Rain",
        icon = R.raw.partly_cloudy_day_rain,
        iconAlt = R.raw.partly_cloudy_night_rain,
        //icon = "http://openweathermap.org/img/wn/10d@2x.png",
        //iconAlt = "http://openweathermap.org/img/wn/10n@2x.png",
    )
    object Rain : WeatherCondition(
        desc = "Rain",
        icon = R.raw.overcast_day_rain,
        iconAlt = R.raw.overcast_night_rain,
        //icon = "http://openweathermap.org/img/wn/10d@2x.png",
        //iconAlt = "http://openweathermap.org/img/wn/10n@2x.png",
    )
    object RainHeavy : WeatherCondition(
        desc = "Heavy Rain",
        icon = R.raw.extreme_day_rain,
        iconAlt = R.raw.extreme_night_rain,
        //icon = "http://openweathermap.org/img/wn/10d@2x.png",
        //iconAlt = "http://openweathermap.org/img/wn/10n@2x.png",
    )
    object RainFreezing : WeatherCondition(
        desc = "Freezing Rain",
        icon = R.raw.overcast_day_sleet,
        iconAlt = R.raw.overcast_night_sleet,
        //icon = "http://openweathermap.org/img/wn/13d@2x.png",
    )
    object RainShowerLight : WeatherCondition(
        desc = "Light Rain Showers",
        icon = R.raw.partly_cloudy_day_rain,
        iconAlt = R.raw.partly_cloudy_night_rain,
        //icon = "http://openweathermap.org/img/wn/09d@2x.png",
    )
    object RainShower : WeatherCondition(
        desc = "Rain Showers",
        icon = R.raw.overcast_day_rain,
        iconAlt = R.raw.partly_cloudy_night_rain,
        //icon = "http://openweathermap.org/img/wn/09d@2x.png",
    )
    object RainShowerHeavy : WeatherCondition(
        desc = "Heavy Rain Showers",
        icon = R.raw.partly_cloudy_day_rain,
        iconAlt = R.raw.partly_cloudy_night_rain,
        //icon = "http://openweathermap.org/img/wn/09d@2x.png",
    )
    object RainShowerScattered : WeatherCondition(
        desc = "Scattered Rain Showers",
        icon = R.raw.partly_cloudy_day_rain,
        iconAlt = R.raw.partly_cloudy_night_rain,
        //icon = "http://openweathermap.org/img/wn/09d@2x.png",
    )
    object SnowLight : WeatherCondition(
        desc = "Light Snow",
        icon = R.raw.partly_cloudy_day_snow,
        iconAlt = R.raw.partly_cloudy_night_snow,
        //icon = "http://openweathermap.org/img/wn/13d@2x.png",
    )
    object Snow : WeatherCondition(
        desc = "Snow",
        icon = R.raw.overcast_day_snow,
        iconAlt = R.raw.overcast_night_snow,
        //icon = "http://openweathermap.org/img/wn/13d@2x.png",
    )
    object SnowHeavy : WeatherCondition(
        desc = "Heavy Snow",
        icon = R.raw.extreme_day_snow,
        iconAlt = R.raw.extreme_night_snow,
        //icon = "http://openweathermap.org/img/wn/13d@2x.png",
    )
    object SnowSleet : WeatherCondition(
        desc = "Sleet",
        icon = R.raw.overcast_day_sleet,
        iconAlt = R.raw.overcast_night_sleet,
        //icon = "http://openweathermap.org/img/wn/13d@2x.png",
    )
    object SnowLightRain : WeatherCondition(
        desc = "Snow w/ Light Rain",
        icon = R.raw.overcast_day_sleet,
        iconAlt = R.raw.overcast_night_sleet,
        //icon = "http://openweathermap.org/img/wn/13d@2x.png",
    )
    object SnowRain : WeatherCondition(
        desc = "Snow w/ Rain",
        icon = R.raw.extreme_day_sleet,
        iconAlt = R.raw.extreme_night_sleet,
        //icon = "http://openweathermap.org/img/wn/13d@2x.png",
    )
    object SnowLightShower : WeatherCondition(
        desc = "Light Snow Showers",
        icon = R.raw.partly_cloudy_day_snow,
        iconAlt = R.raw.partly_cloudy_night_snow,
        //icon = "http://openweathermap.org/img/wn/13d@2x.png",
    )
    object SnowShower : WeatherCondition(
        desc = "Snow Showers",
        icon = R.raw.overcast_day_snow,
        iconAlt = R.raw.overcast_night_snow,
        //icon = "http://openweathermap.org/img/wn/13d@2x.png",
    )
    object SnowHeavyShower : WeatherCondition(
        desc = "Heavy Snow Showers",
        icon = R.raw.extreme_day_snow,
        iconAlt = R.raw.extreme_night_snow,
        //icon = "http://openweathermap.org/img/wn/13d@2x.png",
    )
    object AtmosphericMist : WeatherCondition(
        desc = "Mist",
        icon = R.raw.mist,
        iconAlt = R.raw.mist,
        //icon = "http://openweathermap.org/img/wn/50d@2x.png",
    )
    object AtmosphericSmoke : WeatherCondition(
        desc = "Smoke",
        icon = R.raw.partly_cloudy_day_smoke,
        iconAlt = R.raw.partly_cloudy_night_smoke,
        //icon = "http://openweathermap.org/img/wn/50d@2x.png",
    )
    object AtmosphericHaze : WeatherCondition(
        desc = "Hazy",
        icon = R.raw.partly_cloudy_day_haze,
        iconAlt = R.raw.partly_cloudy_night_haze,
        //icon = "http://openweathermap.org/img/wn/50d@2x.png",
    )
    object AtmosphericDust : WeatherCondition(
        desc = "Dusty",
        icon = R.raw.dust_day,
        iconAlt = R.raw.dust_night,
        //icon = "http://openweathermap.org/img/wn/50d@2x.png",
    )
    object AtmosphericFog : WeatherCondition(
        desc = "Foggy",
        icon = R.raw.overcast_day_fog,
        iconAlt = R.raw.overcast_night_fog,
        //icon = "http://openweathermap.org/img/wn/50d@2x.png",
    )
    object AtmosphericSand : WeatherCondition(
        desc = "Sandstorm",
        icon = R.raw.dust_wind,
        iconAlt = R.raw.dust_wind,
        //icon = "http://openweathermap.org/img/wn/50d@2x.png",
    )
    object AtmosphericVolcanicAsh : WeatherCondition(
        desc = "Volcanic Ash",
        icon = R.raw.extreme_day_smoke,
        iconAlt = R.raw.extreme_night_smoke,
        //icon = "http://openweathermap.org/img/wn/50d@2x.png",
    )
    object AtmosphericSquall : WeatherCondition(
        desc = "Squall",
        icon = R.raw.wind,
        iconAlt = R.raw.wind,
        //icon = "http://openweathermap.org/img/wn/50d@2x.png",
    )
    object AtmosphericTornado : WeatherCondition(
        desc = "Tornado",
        icon = R.raw.tornado,
        iconAlt = R.raw.tornado,
        //icon = "http://openweathermap.org/img/wn/50d@2x.png",
    )
    object Clear : WeatherCondition(
        desc = "Clear Sky",
        icon = R.raw.clear_day,
        iconAlt = R.raw.clear_night,
        //icon = "http://openweathermap.org/img/wn/01d@2x.png",
        //iconAlt = "http://openweathermap.org/img/wn/01n@2x.png",
    )
    object CloudyPartly : WeatherCondition(
        desc = "Partly Cloudy",
        icon = R.raw.partly_cloudy_day,
        iconAlt = R.raw.partly_cloudy_night,
        //icon = "http://openweathermap.org/img/wn/02d@2x.png",
        //iconAlt = "http://openweathermap.org/img/wn/02n@2x.png",
    )
    object CloudyScattered : WeatherCondition(
        desc = "Scattered Clouds",
        icon = R.raw.partly_cloudy_day,
        iconAlt = R.raw.partly_cloudy_night,
        //icon = "http://openweathermap.org/img/wn/03d@2x.png",
    )
    object CloudyMostly : WeatherCondition(
        desc = "Mostly Cloudy",
        icon = R.raw.cloudy,
        iconAlt = R.raw.cloudy,
        //icon = "http://openweathermap.org/img/wn/04d@2x.png",
    )
    object CloudyOvercast : WeatherCondition(
        desc = "Overcast",
        icon = R.raw.overcast_day,
        iconAlt = R.raw.overcast_night,
        //icon = "http://openweathermap.org/img/wn/04d@2x.png",
    )

}


