package com.masranber.midashboard.util.units

class Quantity<T : Dimension> (val value: Double, val unit: DerivedUnit<T>) {

    constructor(copy: Quantity<T>) : this(copy.value, copy.unit)

    infix fun to(other: DerivedUnit<T>) : Quantity<T> {
        return Quantity(other.fromBaseUnit(this.unit.toBaseUnit(value)), other)
    }

    operator fun plus(other: Quantity<T>) : Quantity<T> {
        return Quantity(this.unit.fromBaseUnit(this.unit.toBaseUnit(this.value) + other.unit.toBaseUnit(other.value)), this.unit)
    }

    operator fun minus(other: Quantity<T>) : Quantity<T> {
        return Quantity(this.unit.fromBaseUnit(this.unit.toBaseUnit(this.value) - other.unit.toBaseUnit(other.value)), this.unit)
    }
}

infix fun <A : Number, T : Dimension, B : DerivedUnit<T>> A.to(that: B): Quantity<T> {
    return Quantity(this.toDouble(), that)
}