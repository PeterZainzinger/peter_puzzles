package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.Point3
import shared.splitLines
import y2021.Aoc2021D01

class Y2021Day06Test : BaseTest<List<Int>>(2021, 6) {

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2021D06.countAfterDay(input, 80)
        assertNumber(358214, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2021D06.countAfterDay(input, 256)
        assertNumber(1622533344325, res)
    }


    override fun parseInput(input: String) = input.split(",").map { it.toInt() }
}
