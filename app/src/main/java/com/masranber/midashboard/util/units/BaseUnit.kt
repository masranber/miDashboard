package com.masranber.midashboard.util.units

open class BaseUnit<T : Dimension>(symbol: String) : DerivedUnit<T>(symbol) {
    override fun toBaseUnit(derived: Double): Double {
        return derived
    }
    override fun fromBaseUnit(base: Double): Double {
        return base
    }
}