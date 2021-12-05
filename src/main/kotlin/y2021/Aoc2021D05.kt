package y2021

import shared.Point
import java.lang.Exception
import kotlin.math.absoluteValue
import kotlin.math.sign

object Aoc2021D05 {

    fun countOverLaps(points: List<Pair<Point, Point>>, includeDiagonal: Boolean): Int = points.flatMap { (a, b) ->
        val vecNN = (b.x - a.x) to (b.y - a.y)
        when {
            (a.x == b.x).or(a.y == b.y) -> {
                val distance = a manhattenDistance b
                val vec = (b.x - a.x) / distance to (b.y - a.y) / distance
                (0..distance).map {
                    Point(x = a.x + vec.first * it, y = a.y + vec.second * it)
                }
            }
            vecNN.first.absoluteValue == vecNN.second.absoluteValue && includeDiagonal -> {
                val vec = Point(vecNN.first.sign, vecNN.second.sign)
                val distance = vecNN.first.absoluteValue
                (0..distance).map {
                    Point(x = a.x + vec.x * it, y = a.y + vec.y * it)
                }
            }
            else -> {
                emptyList()
            }
        }
    }.groupBy { it }.entries.filter { it.value.size > 1 }.size

}
