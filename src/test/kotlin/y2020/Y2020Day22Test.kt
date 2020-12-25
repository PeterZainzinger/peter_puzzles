package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitEmptyLines

class Y2020Day22Test : BaseTest<Pair<List<Int>, List<Int>>>(2020, 22) {
    private fun parsePlayer(input: String): List<Int> {
        val lines = input.split("\n")
        return lines.subList(1, lines.size).map { it.toInt() }
    }

    override fun parseInput(input: String): Pair<List<Int>, List<Int>> {
        val (i1, i2) = input.splitEmptyLines()
        val statePlayer1 = parsePlayer(i1)
        val statePlayer2 = parsePlayer(i2)
        return statePlayer1 to statePlayer2
    }

    @Test
    fun part1() {
        val (statePlayer1, statePlayer2) = getInput()
        val res = Aoc2020D22.part1(statePlayer1, statePlayer2)
        assertNumber(33473, res)
    }

    @Test
    fun part2() {
        val (statePlayer1, statePlayer2) = getInput()
        val res = Aoc2020D22.part2(statePlayer1, statePlayer2)
        assertNumber(31793, res)
    }
}
