package y2021

import shared.Point
import kotlin.math.abs
import kotlin.math.absoluteValue

object Aoc2021D09 {
    data class PointWithValue(val point: Point, val value: Int)

    private val neighborOffsetsNotDiagonal =
        (-1..1).flatMap { x ->
            (-1..1).map { y ->
                Point(x, y)
            }
        }
            .filter { (it.x != 0).or(it.y != 0) }
            .filter { !(it.x.absoluteValue == 1 && it.y.absoluteValue == 1) }


     fun findLowPoints(input: List<PointWithValue>): List<PointWithValue> {
        val map = input.associate { it.point to it.value }

        val lowPoints = input.filter { p ->
            val neighbors = neighborOffsetsNotDiagonal.mapNotNull { map[p.point + it] }
            p.value < neighbors.minOrNull()!!
        }
        return lowPoints
    }

     fun getRisk(lowPoints: List<PointWithValue>): Int {
         return lowPoints.sumOf { it.value + 1 }
    }

     fun getBasins(lowPoints: List<PointWithValue>, input: List<PointWithValue>): List<Set<Point>> {
         val map = input.associate { it.point to it.value }
         val allBasis = lowPoints.mapIndexed { i, it ->
             getBasin(it, map, emptySet())
         }
         return allBasis.sortedBy { it.size }.reversed()
    }


    private fun getBasin(p: PointWithValue, map: Map<Point, Int>, currentPoints: Set<Point>): Set<Point> {
        val neighbors = neighborOffsetsNotDiagonal.mapNotNull { o ->
            val p = p.point + o
            val i = map[p]
            when (i) {
                null -> null
                else -> PointWithValue(p, i)
            }
        }.filter { !currentPoints.contains(it.point) }
        val newNeighbors = neighbors.filter { it.value < 9 && it.value >= p.value   }
        val res = newNeighbors.map { it.point }.toSet() + (newNeighbors.flatMap {
            getBasin(
                it,
                map,
                currentPoints + setOf(p.point)

            )
        }).toSet()
        return res + setOf(p.point)

    }




}

