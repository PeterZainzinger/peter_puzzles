package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines
import y2020.Aoc2020D18.eval

class Y2020Day18Test : BaseTest<List<String>>(2020, 18) {

    override fun parseInput(input: String) = input.splitLines()

    @Test
    fun part1() {
        val input = getInput()
        val res = input.map { eval(it, false) }.sum()
        assertNumber(8298263963837L, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = input.map { eval(it, true) }.sum()
        assertNumber(145575710203332, res)
    }
}
