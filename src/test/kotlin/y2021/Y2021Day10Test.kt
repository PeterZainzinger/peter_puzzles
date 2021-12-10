package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.Point3
import shared.splitLines
import y2021.Aoc2021D01

class Y2021Day10Test : BaseTest<List<String>>(2021, 10) {

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2021D10.part1(input)
        assertNumber(343863, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2021D10.part2(input)
        assertNumber(2924734236L, res)
    }

    override fun parseInput(input: String) = input.splitLines()
}
