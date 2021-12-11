package y2021

import shared.Point
import java.lang.Exception

object Aoc2021D11 {

    fun countExplosions(gen: Int, input: Map<Point, Int>): Int {
        val universe = Universe.fromPoints(input)
        var explosions = 0
        repeat(gen) {
            val newFlashes = universe.iterate()
            explosions += newFlashes
        }

        return explosions
    }

    fun syncGen(input: Map<Point, Int>): Int {
        val universe = Universe.fromPoints(input)
        var explosions = 0
        var step = 0
        while (true) {
            val newFlashes = universe.iterate()
            explosions += newFlashes
            if (newFlashes == 100) {
                return step + 1
            }
            step++
        }
    }
}


private val neighbors =
    (-1..1).flatMap { x ->
        (-1..1).map { y ->
            Point(x, y)
        }
    }
        .filter { (it.x != 0).or(it.y != 0) }


data class Cell(val p: Point, var neighbors: List<Cell>, var value: Int) {
    fun explode() {
        // new explosion
        if (value == 10) {
            // to not flash again
            value = 11
            neighbors.forEach {
                if (it.value != 10) {
                    it.value += 1
                }
                it.explode()
            }
        }
    }
}

data class Universe(val cells: Map<Point, Cell>) {

    companion object {
        fun fromPoints(input: Map<Point, Int>): Universe {
            val cellsMap = input.entries.map { Cell(it.key, emptyList(), value = it.value) }.associateBy { it.p }

            cellsMap.entries.forEach { (key, value) ->
                value.neighbors = neighbors.mapNotNull { cellsMap[key + it] }
            }
            return Universe(cellsMap)
        }
    }

    override fun toString() = (0 until 10).map { y ->
        val line = (0 until 10).map { x ->
            cells[Point(x, y)]?.value
        }.joinToString("")
        line
    }.joinToString("\n")

    fun iterate(): Int {
        increase()
        explode()
        return countExplosionsAndSet()
    }

    private fun increase() {
        cells.values.forEach { it.value += 1 }
    }

    private fun explode() {
        cells.forEach { (t, u) -> u.explode() }
    }

    private fun countExplosionsAndSet(): Int {
        return cells.values.count {
            val explode = it.value > 9
            if (explode) {
                it.value = 0
            }
            explode
        }
    }
}


