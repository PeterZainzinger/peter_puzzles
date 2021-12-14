package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.Point3
import shared.splitLines
import y2021.Aoc2021D01

class Y2021Day14Test : BaseTest<Aoc2021D14.Input>(2021, 14) {
    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2021D14.iterate(input, 10)
        assertNumber(2321L, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2021D14.iterate(input, 40)
        assertNumber(2399822193707L, res)
    }

    override fun parseInput(input: String) =
        Aoc2021D14.Input.parse(input)
}
