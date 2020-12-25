package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines

class Y2020Day01Test : BaseTest<List<Int>>(2020, 1) {

    override fun parseInput(input: String) = input.splitLines().map { it.toInt() }

    @Test
    fun part1() {
        val input = getInput()
        val (i, j) = Aoc2020D01.findPair(input, 2020);
        assertNumber(1018944, i * j)

    }

    @Test
    fun part2() {
        val input = getInput()
        val (a, b, c) = Aoc2020D01.findTriple(input, 2020)
        assertNumber(8446464, a * b * c)
    }
}