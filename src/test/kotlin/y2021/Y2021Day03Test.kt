package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.Point3
import shared.splitLines
import y2021.Aoc2021D01

class Y2021Day03Test : BaseTest<List<String>>(2021, 3) {

    @Test
    fun part1() {
        val input = getInput()
        val x = Aoc2021D03.calcGammaAndEpsilon(input)
        assertNumber(3309596, x)
    }

    @Test
    fun part2() {
        val input = getInput()
        val ox = Aoc2021D03.calcOxygonRating( input)
        val co2 = Aoc2021D03.co2( input)
        assertNumber(2981085, ox * co2)
    }

    override fun parseInput(input: String): List<String> = input.splitLines()
}
