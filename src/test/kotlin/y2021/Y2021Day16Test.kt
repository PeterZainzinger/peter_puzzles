package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.splitLines

class Y2021Day16Test : BaseTest<String>(2021, 16) {

    @Test
    fun part1() {
        val input = getInput()
        val (tree) = Aoc2021D16.parsePacket(Aoc2021D16.decode(input))
        assertNumber(949, Aoc2021D16.versionSum(tree.first()))
    }

    @Test()
    fun part2() {
        val input = getInput()
        val (tree) = Aoc2021D16.parsePacket(Aoc2021D16.decode(input))
        assertNumber(1114600142730, tree.first().eval())
    }

    override fun parseInput(input: String) =
        input
}
