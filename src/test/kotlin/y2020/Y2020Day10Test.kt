package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines

class Y2020Day10Test : BaseTest<List<Int>>(2020, 10) {

    override fun parseInput(input: String) = input.splitLines().map { it.toInt() }.sorted()

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2020D10.part1(input)
        assertNumber(2244, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2020D10.countArrangements(input)
        assertNumber(3947645370368L, res)
    }
}
