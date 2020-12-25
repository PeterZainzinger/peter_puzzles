package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitEmptyLines
import shared.splitLines

class Y2020Day06Test : BaseTest<List<List<String>>>(2020, 6) {

    override fun parseInput(input: String) = input.splitEmptyLines().map {
        it.splitLines()
    }

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2020D06.exercise1(input)
        assertNumber(6416, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2020D06.exercise2(input)
        assertNumber(3050, res)
    }
}