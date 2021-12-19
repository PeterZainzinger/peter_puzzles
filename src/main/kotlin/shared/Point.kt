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

    override fun toString() = "(${x},${y})"
}


data class Point3(val x: Int, val y: Int, val z: Int) {
    companion object {
        val zero = Point3(0, 0, 0)
    }

    operator fun plus(other: Point3) = Point3(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Point3) = this + (other * -1)

    operator fun times(value: Int) = Point3(x * value, y * value, z * value)

    infix fun manhattenDistance(other: Point3) = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)

    override fun toString() = "(${x},${y},${z})"

    val entries: List<Int>
        get() = listOf(x, y, z)


}
