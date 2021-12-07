package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.Point3
import shared.splitLines
import y2021.Aoc2021D01

class Y2021Day07Test : BaseTest<List<Int>>(2021, 7) {

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2021D07.findLowestCost1(input)
        assertNumber(352707, res.second)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2021D07.findLowestCost2(input)
        assertNumber(95519725, res.second)
    }


    override fun parseInput(input: String) = input.split(",").map { it.toInt() }
}
