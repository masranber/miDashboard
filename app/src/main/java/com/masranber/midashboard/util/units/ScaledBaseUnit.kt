package com.masranber.midashboard.util.units

class ScaledBaseUnit<T : Dimension>(symbol: String, val scalar: Double) : BaseUnit<T>(symbol) {
    override fun toBaseUnit(derived: Double): Double {
        return super.toBaseUnit(derived) * scalar
    }

    override fun fromBaseUnit(base: Double): Double {
        return super.fromBaseUnit(base) / scalar
    }
}