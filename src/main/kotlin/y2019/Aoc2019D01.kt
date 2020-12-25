package y2019

import kotlin.math.floor

object Aoc2019D01 {

    fun fuelWithFuel(input: Int, include: Boolean): Int {
        val res = floor(input / 3.0).toInt() - 2
        return when {
            res < 0 -> 0
            else -> res + when (include) {
                true -> fuelWithFuel(res, include)
                else -> 0
            }
        }
    }
}
