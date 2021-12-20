package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point3
import shared.splitLines

class Y2021Day20Test : BaseTest<Aoc2021D20.Picture>(2021, 20) {

    @Test
    fun part1() {
        val input = getInput()
        val res = input.pictureAfterSteps(2).lightenUp
        assertNumber(5819, res)
    }

    @Test()
    fun part2() {
        val input = getInput()
        val res = input.pictureAfterSteps(50).lightenUp
        assertNumber(18516, res)
    }


    override fun parseInput(input: String) = Aoc2021D20.Picture.parse(input)
}
