package com.masranber.midashboard.util.units

val Number.tera  get() = this.toDouble() * Math.pow(10.0, +12.0)
val Number.giga  get() = this.toDouble() * Math.pow(10.0, +9.0)
val Number.mega  get() = this.toDouble() * Math.pow(10.0, +6.0)
val Number.kilo  get() = this.toDouble() * Math.pow(10.0, +3.0)
val Number.hecto get() = this.toDouble() * Math.pow(10.0, +2.0)
val Number.deka  get() = this.toDouble() * Math.pow(10.0, +1.0)
val Number.deci  get() = this.toDouble() * Math.pow(10.0, -1.0)
val Number.centi get() = this.toDouble() * Math.pow(10.0, -2.0)
val Number.milli get() = this.toDouble() * Math.pow(10.0, -3.0)
val Number.micro get() = this.toDouble() * Math.pow(10.0, -6.0)
val Number.nano  get() = this.toDouble() * Math.pow(10.0, -9.0)
