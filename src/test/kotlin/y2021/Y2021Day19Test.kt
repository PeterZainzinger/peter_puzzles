package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point3
import shared.splitLines

class Y2021Day19Test : BaseTest<List<Aoc2021D19.Scanner>>(2021, 19) {

    @Test
    fun part1() {
        val input = getInput()
        val (res, _) = Aoc2021D19.solve(input)
        assertNumber(372, res.size)
    }

    @Test()
    fun part2() {
        val input = getInput()
        val (_, res) = Aoc2021D19.solve(input)
        assertNumber(12241, res!!)
    }


    override fun parseInput(input: String) = input.split("\n\n").mapIndexed { index, text ->
        val scans = text.splitLines().drop(1).map { m ->
            val (x, y, z) = m.split(",")
            Point3(x.toInt(), y.toInt(), z.toInt())
        }
        Aoc2021D19.Scanner(index, scans)
    }
}
