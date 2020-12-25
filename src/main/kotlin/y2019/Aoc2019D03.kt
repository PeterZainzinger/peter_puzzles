package y2019

import shared.Point
import shared.then

object Aoc2019D03 {
    class WireEdge(
        private val x: Int,
        private val y: Int,
        private val value: Int
    ) {
        fun move(pos: Point) = Point(pos.x + x * value, pos.y + y * value)
    }

    fun energyEffiecientPoint(inputA: List<WireEdge>, inputB: List<WireEdge>): Int {
        val (intersects, a, b) = intersectionPoints(inputA, inputB)
        return intersects.map { (a.indexOf(it) to b.indexOf(it)) }.filterNot { (x, y) -> x == 0 || y == 0 }
            .map { it.first + it.second }.min()!!
    }

    fun closesIntersectionPoint(inputA: List<WireEdge>, inputB: List<WireEdge>): Point {
        val (intersects, _, _) = intersectionPoints(inputA, inputB)
        return intersects.filterNot { it == Point.zero }.map {
            (Point.zero manhattenDistance it) to it
        }.minByOrNull { it.first }!!.second
    }

    private fun intersectionPoints(
        inputA: List<WireEdge>,
        inputB: List<WireEdge>
    ): Triple<List<Point>, List<Point>, List<Point>> {
        val a = expandAllPointsBetweenPoints(listOf(Point.zero) + positionOfOp(inputA, Point.zero))
        val b = expandAllPointsBetweenPoints(listOf(Point.zero) + positionOfOp(inputB, Point.zero))
        return a.toSet().intersect(b.toSet()).filterNot { it == Point.zero } then a then b
    }

    private fun positionOfOp(input: List<WireEdge>, currentPos: Point): List<Point> {
        if (input.isEmpty()) {
            return emptyList()
        }
        val op = input.first()
        val newPos = op.move(currentPos)
        return listOf(newPos) + positionOfOp(input.subList(fromIndex = 1, toIndex = input.size), newPos)
    }

    private fun expandAllPointsBetweenPoints(input: List<Point>) = input.mapIndexed { index, position ->
        when (index) {
            0 -> listOf()
            else -> {
                val prev = input[index - 1]
                val distance = prev manhattenDistance position
                val vec = (position.x - prev.x) / distance to (position.y - prev.y) / distance
                (0 until distance).map {
                    Point(x = prev.x + vec.first * it, y = prev.y + vec.second * it)
                }
            }
        }
    }.flatten()
}
