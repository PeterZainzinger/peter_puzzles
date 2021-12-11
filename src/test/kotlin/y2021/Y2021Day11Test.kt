package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.Point3
import shared.splitLines
import y2021.Aoc2021D01

class Y2021Day11Test : BaseTest<Map<Point, Int>>(2021, 11) {

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2021D11.countExplosions(100, input)
        assertNumber(1655, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2021D11.syncGen(input)
        assertNumber(337, res)
    }

    override fun parseInput(input: String) =
        input.splitLines().flatMapIndexed { y, s -> s.splitChars().mapIndexed { x, i -> Point(x, y) to i.toInt() } }
            .toMap()
}
