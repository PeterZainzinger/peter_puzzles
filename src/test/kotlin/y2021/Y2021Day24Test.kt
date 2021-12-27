package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point3
import shared.splitLines
import kotlin.math.max

class Y2021Day24Test : BaseTest<List<Aoc2021D24.Exp>>(2021, 24) {


    @Test
    // should take about 6 minutes..
    fun part1And2() {
        val input = getInput()
        val (highest,lowest) = Aoc2021D24.search(input)
        assertNumber(highest, 79197919993985)
        assertNumber(lowest, 13191913571211)
    }

    override fun parseInput(text: String) =  text.splitLines().map { Aoc2021D24.Exp.parse(it) }
}
