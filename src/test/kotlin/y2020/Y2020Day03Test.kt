package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines

class Y2020Day03Test : BaseTest<List<String>>(2020, 3) {

    override fun parseInput(input: String) = input.splitLines()

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2020D03.countHits(input)
        assertNumber(257, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2020D03.exercise2(input)
        assertNumber(1744787392, res)
    }

}