package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.Point3
import shared.splitLines
import y2021.Aoc2021D01

class Y2021Day05Test : BaseTest<List<Pair<Point, Point>>>(2021, 5) {

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2021D05.countOverLaps(input, false)
        assertNumber(6311, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2021D05.countOverLaps(input, true)
        assertNumber(19929, res)
    }


    override fun parseInput(input: String) = input.splitLines().map {
        val (a, b) = it.split(" -> ").map {
            val (x, y) = it.split(",")
            Point(x.toInt(), y.toInt())
        }
        a to b
    }
}
