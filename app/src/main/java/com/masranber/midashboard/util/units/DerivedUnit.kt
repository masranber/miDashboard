package com.masranber.midashboard.util.units

abstract class DerivedUnit<T : Dimension>(val symbol: String) {
    abstract fun toBaseUnit(derived: Double) : Double
    abstract fun fromBaseUnit(base: Double) : Double
}