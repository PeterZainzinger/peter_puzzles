package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.Point3
import shared.splitLines
import y2021.Aoc2021D01

class Y2021Day08Test : BaseTest<List<Aoc2021D08.Line>>(2021, 8) {

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2021D08.solvePart2(input)
        assertNumber(933305, res)
    }

    override fun parseInput(input: String) = input.splitLines().map { Aoc2021D08.Line.parse(it) }
}
