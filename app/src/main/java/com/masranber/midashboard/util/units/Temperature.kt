package com.masranber.midashboard.util.units

sealed class DimenTemp : Dimension()

object kelvin : BaseUnit<DimenTemp>("K")

object celsius : DerivedUnit<DimenTemp>("°C") {

    override fun toBaseUnit(derived: Double): Double {
        return derived + 273.15
    }

    override fun fromBaseUnit(base: Double): Double {
        return base - 273.15
    }
}

object fahrenheit : DerivedUnit<DimenTemp>("°F") {

    override fun toBaseUnit(derived: Double): Double {
        return (derived - 32) * (5.0/9.0) + 273.15
    }

    override fun fromBaseUnit(base: Double): Double {
        return (base - 273.15) * (9.0/5.0) + 32
    }
}

typealias Temperature = Quantity<DimenTemp>

val Number.celsius get() = Temperature(this.toDouble(), com.masranber.midashboard.util.units.celsius)
val Number.fahrenheit get() = Temperature(this.toDouble(), com.masranber.midashboard.util.units.fahrenheit)
val Number.kelvin get() = Temperature(this.toDouble(), com.masranber.midashboard.util.units.kelvin)