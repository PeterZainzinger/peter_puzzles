package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point3
import shared.splitLines
import kotlin.math.max

class Y2021Day25Test : BaseTest<Aoc2021D25.Input>(2021, 25) {

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2021D25.howManyToStop(input)
        assertNumber(321, res)
    }

    override fun parseInput(text: String) = Aoc2021D25.Input.parse(text)
}
