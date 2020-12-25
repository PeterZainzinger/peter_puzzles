package y2019

import Aoc2019D4
import BaseTest
import assertNumber
import org.junit.jupiter.api.Test

class Y2019Day4Test : BaseTest<IntRange>(2019, 4) {

    override fun parseInput(input: String) = 0..0

    @Test
    fun part1() {
        val input = 256310..732736
        val options = Aoc2019D4.searchPasswordInRange(input)
        assertNumber(979, options.size)
    }

    @Test
    fun part2() {
        val input = 256310..732736
        val options = Aoc2019D4.searchPasswordInRange(input)
        val optionsB = Aoc2019D4.searchMore(options)
        assertNumber(635, optionsB.size)
    }

}