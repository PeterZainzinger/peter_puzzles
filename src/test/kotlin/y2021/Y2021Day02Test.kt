package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.Point3
import shared.splitLines
import y2021.Aoc2021D01

class Y2021Day02Test : BaseTest<List<Aoc2021D02.Command>>(2021, 2) {

    @Test
    fun part1() {
        val input = getInput()
        val (x, y) = Aoc2021D02.executeCommands(Point.zero, input)
        assertNumber(1660158, x * y)
    }

    @Test
    fun part2() {
        val input = getInput()
        val (x, y) = Aoc2021D02.executeCommands2(Point3.zero, input)
        assertNumber(1604592846, x * y)

    }

    override fun parseInput(input: String): List<Aoc2021D02.Command> = input.splitLines().map { Aoc2021D02.parse(it) }
}
