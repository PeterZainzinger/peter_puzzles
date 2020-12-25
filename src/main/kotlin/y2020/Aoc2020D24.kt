package y2020

import shared.Point

object Aoc2020D24 {
    data class DirectionInstruction(val prefix: String, val direction: Point) {
        companion object {
            val all = listOf(
                DirectionInstruction("e", Point(2, 0)),
                DirectionInstruction("ne", Point(1, 1)),
                DirectionInstruction("nw", Point(-1, 1)),
                DirectionInstruction("w", Point(-2, 0)),
                DirectionInstruction("sw", Point(-1, -1)),
                DirectionInstruction("se", Point(1, -1)),
            )
        }
    }

    private fun allNeighbors(vector: Point) = DirectionInstruction.all.map { it.direction + vector }

    fun buildState(input: List<List<DirectionInstruction>>): Map<Point, Boolean> {
        val flippedStates = mutableMapOf<Point, Boolean>()
        input.forEach { identifier ->
            val resultingId = identifier.fold(Point(0, 0), { acc, new -> acc + new.direction })
            val currentState = flippedStates.getOrDefault(resultingId, false)
            flippedStates[resultingId] = !currentState
        }
        return flippedStates.toMap()
    }

    fun gameOfLife(input: Map<Point, Boolean>, loops: Int): Map<Point, Boolean> {
        var flippedStates = input.toMutableMap()
        var day = 0
        while (day < loops) {
            val allKeysToConsider = flippedStates.keys.flatMap { allNeighbors(it) }.toSet()
            flippedStates = allKeysToConsider.map { key ->
                val blackNeigbors = allNeighbors(key).filter { flippedStates[it] == true }.size
                val isBlack = flippedStates[key] ?: false
                key to when {
                    isBlack -> blackNeigbors in 1..2
                    else -> blackNeigbors == 2
                }
            }.toMap().toMutableMap()
            day++
        }
        return flippedStates.toMap()
    }
}
