package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines
import y2020.Aoc2020D09.findContinousSum
import y2020.Aoc2020D09.findNonMatching

class Y2020Day09Test : BaseTest<List<Long>>(2020, 9) {

    override fun parseInput(input: String) = input.splitLines().map { it.toLong() }

    @Test
    fun part1() {
        val input = getInput()
        val res = findNonMatching(input, 25, 25)
        assertNumber(14144619, res.first())
    }

    @Test
    fun part2() {
        val input = getInput()
        val target2 = findNonMatching(input, 25, 25).first()
        val cs = findContinousSum(target2, input)!!
        val res = cs.minOrNull()!! + cs.maxOrNull()!!
        assertNumber(1766397, res)
    }
}
