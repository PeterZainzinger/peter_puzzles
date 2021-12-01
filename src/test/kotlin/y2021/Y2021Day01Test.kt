package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines
import y2021.Aoc2021D01

class Y2021Day01Test : BaseTest<List<Int>>(2021, 1) {

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2021D01.howManyIncreasing(input)
        assertNumber(1583, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2021D01.howManyIncreasingSliding(input)
        assertNumber(1627, res)
    }

    override fun parseInput(input: String): List<Int> = input.splitLines().map { it.toInt() }
}
