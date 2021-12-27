package y2021

import shared.*
import java.io.File
import java.util.*
import kotlin.math.pow

object Aoc2021D25 {

    enum class Direction(val vec: Point) {
        east(Point(1, 0)),
        south(Point(0, 1));

        companion object {
            fun parse(char: String) = when (char) {
                ">" -> east
                "v" -> south
                else -> null
            }
        }

        fun next(p: Point, dims: Point): Point {
            val next = p + vec
            return Point(next.x % dims.x, next.y % dims.y)
        }
    }

    private fun moveInMap(map: Map<Point, Direction>, f: (Direction) -> Boolean, dims: Point): Map<Point, Direction> =
        map.entries.associate { (key, value) ->
            when (f(value)) {
                true -> when (map[value.next(key, dims)]) {
                    null -> value.next(key, dims) to value
                    else -> key to value
                }
                else -> key to value
            }
        }

    fun move(map: Map<Point, Direction>, dims: Point): Map<Point, Direction> {
        val inter = moveInMap(map, { it == Direction.east }, dims)
        return moveInMap(inter, { it == Direction.south }, dims)
    }

    fun howManyToStop(input: Input): Int {
        var before: Map<Point, Direction>? = null
        var current: Map<Point, Direction> = input.map
        var i = 0
        while (before != current) {
            val inter = current
            current = move(current, input.dimensions)
            before = inter
            i++
        }
        return i
    }

    data class Input(val map: Map<Point, Direction>, val dimensions: Point) {
        companion object {
            fun parse(text: String): Input {
                val lines = text.lines()
                val dimensions = Point(lines.first().length, lines.size)
                val map =
                    lines.flatMapIndexed { y, l ->
                        l.splitChars().mapIndexed { x, s ->
                            when (val entry = Direction.parse(s)) {
                                null -> null
                                else -> Point(x, y) to entry
                            }
                        }.filterNotNull()
                    }.toMap()
                return Input(map, dimensions)

            }
        }
    }

}


