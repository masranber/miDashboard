package com.masranber.midashboard.util.units

class _speed : Dimension()

object m_s : BaseUnit<_speed>("m/s")

object mph : DerivedUnit<_speed>("mph") {
    override fun toBaseUnit(derived: Double): Double {
        return derived / 2.23694
    }

    override fun fromBaseUnit(base: Double): Double {
        return base * 2.23694
    }
}

object m_h : DerivedUnit<_speed>("mh") {
    override fun toBaseUnit(derived: Double): Double {
        return derived / 3600.0
    }

    override fun fromBaseUnit(base: Double): Double {
        return base * 3600.0
    }
}

typealias Speed = Quantity<_speed>

val Number.m_s get() = Speed(this.toDouble(), com.masranber.midashboard.util.units.m_s)
val Number.mph get() = Speed(this.toDouble(), com.masranber.midashboard.util.units.mph)
val Number.m_h get() = Speed(this.toDouble(), com.masranber.midashboard.util.units.m_h)