package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point3
import shared.splitLines
import kotlin.math.max

class Y2021Day23Test : BaseTest<Aoc2021D23.AmphipodState>(2021, 23) {


    // who cares about part 1 anyway

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2021D23.search(input)
        assertNumber(49742, res)
    }



    override fun parseInput(text: String) =  Aoc2021D23.AmphipodState.parse(text)
}
