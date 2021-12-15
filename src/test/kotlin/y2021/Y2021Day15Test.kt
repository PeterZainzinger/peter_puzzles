package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.splitLines

class Y2021Day15Test : BaseTest<Map<Point, Int>>(2021, 15) {

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2021D15.pathWeight(input)
        assertNumber(456, res)
    }

    // takes a while
    @Test()
    fun part2() {
        val input = getInput()
        val res = Aoc2021D15.pathWeight(Aoc2021D15.constructBiggerMap(input, 5))
        assertNumber(2831, res)
    }

    override fun parseInput(input: String) =
        input.splitLines().mapIndexed { y, s -> s.splitChars().mapIndexed { x, d -> Point(x, y) to d.toInt() } }
            .flatten().toMap()
}
