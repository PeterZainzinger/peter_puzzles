package shared

import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
    companion object {
        val zero = Point(0, 0)
    }

    val manhattenDistanceToZero
        get() = zero manhattenDistance this

    infix fun manhattenDistance(other: Point) = abs(x - other.x) + abs(y - other.y)

    operator fun plus(other: Point) = Point(x + other.x, y + other.y)

    operator fun times(value: Int) = Point(x * value, y * value)
}
