package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test

class Y2021Day17Test : BaseTest<Aoc2021D17.TargetArea>(2021, 17) {

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2021D17.findHighestYValueCanHit(input)
        assertNumber(3570, res)
    }

    @Test()
    fun part2() {
        val input = getInput()
        val res = Aoc2021D17.findCount(input)
        assertNumber(1919, res)
    }

    override fun parseInput(input: String) =
        Aoc2021D17.TargetArea(248..285, -85..-56)
}
