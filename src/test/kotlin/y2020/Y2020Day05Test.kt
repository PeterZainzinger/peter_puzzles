package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines

class Y2020Day05Test : BaseTest<List<Aoc2020D05.Line>>(2020, 5) {

    override fun parseInput(input: String) = input.splitLines().map {
        Aoc2020D05.Line(
            it.substring(0, 7),
            it.substring(7, 10)
        )
    }

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2020D05.exercise1(input)
        assertNumber(838, res!!)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2020D05.exercise2(input)
        assertNumber(714, res.first()!!)
    }

}