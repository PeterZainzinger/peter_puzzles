package y2019

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines

class Y2019Day01Test : BaseTest<List<Int>>(2019, 1) {

    override fun parseInput(input: String) = input.splitLines().map { it.toInt() }

    @Test
    fun part1() {
        val input = getInput()
        assertNumber(3452245, input.map { Aoc2019D01.fuelWithFuel(it, false) }.sum())
    }

    @Test
    fun part2() {
        val input = getInput()
        assertNumber(5175499, input.map { Aoc2019D01.fuelWithFuel(it, true) }.sum())
    }
}
