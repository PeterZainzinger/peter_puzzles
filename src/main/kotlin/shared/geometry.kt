package shared

import kotlin.math.abs

data class Point(val x: Int, val y: Int) {
    infix fun manhattenDistance(other: Point) = abs(x - other.x) + abs(y - other.y)

    val manhattenDistanceToZero
        get() = zero manhattenDistance this

    companion object {
        val zero = Point(0, 0)
    }
}

