package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitComma
import y2020.Aoc2020D15.findNth

class Y2020Day15Test : BaseTest<List<Int>>(2020, 15) {

    override fun parseInput(input: String) = input.splitComma().map { it.toInt() }

    @Test
    fun part1() {
        val input = getInput()
        val res = findNth(input, 2020)
        assertNumber(866, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = findNth(input, 30000000)
        assertNumber(1437692, res)
    }
}
