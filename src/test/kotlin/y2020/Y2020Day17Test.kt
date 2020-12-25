package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines
import y2020.Aoc2020D17.newState

class Y2020Day17Test : BaseTest<Set<Aoc2020D17.Index>>(2020, 17) {

    override fun parseInput(input: String) = input.splitLines()
        .mapIndexed { y, row ->
            row.mapIndexedNotNull { x, c ->
                when (c) {
                    '#' -> Aoc2020D17.Index(x, y, 0, 0)
                    else -> null
                }
            }
        }.flatten().toSet()

    @Test
    fun part2() {
        val input = getInput()
        val resState = (0 until 6).fold(input.toSet()) { acc, _ -> newState(acc) }
        val res = resState.size
        assertNumber(1504, res)
    }
}
