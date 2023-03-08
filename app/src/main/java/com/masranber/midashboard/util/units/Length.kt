package com.masranber.midashboard.util.units

class _length : Dimension()

object meters : BaseUnit<_length>("m")

object miles : DerivedUnit<_length>("mi") {
    override fun toBaseUnit(derived: Double): Double {
        return derived * 1609.344
    }

    override fun fromBaseUnit(base: Double): Double {
        return base / 1609.344
    }
}

typealias Length = Quantity<_length>

val Number.meters get() = Length(this.toDouble(), com.masranber.midashboard.util.units.meters)
val Number.miles get() = Length(this.toDouble(), com.masranber.midashboard.util.units.miles)
