package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.Point3
import shared.splitLines
import y2021.Aoc2021D01

class Y2021Day09Test : BaseTest<List<Aoc2021D09.PointWithValue>>(2021, 9) {

    @Test
    fun part1() {
        val input = getInput()
        val lowPoints = Aoc2021D09.findLowPoints(input)
        val risk = Aoc2021D09.getRisk(lowPoints)
        assertNumber(537, risk)
    }

    @Test
    fun part2() {
        val input = getInput()
        val lowPoints = Aoc2021D09.findLowPoints(input)
        val basins = Aoc2021D09.getBasins(lowPoints, input)
        assertNumber(1142757, basins.subList(0, 3).map { it.size.toLong() }.fold(1L) { a, b -> a * b })
    }

    override fun parseInput(input: String) = input.splitLines().flatMapIndexed { y: Int, l: String ->
        l.splitChars().mapIndexed { x: Int, s: String ->
            Aoc2021D09.PointWithValue(Point(x, y), s.toInt())
        }
    }
}
