package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point3
import shared.splitLines
import kotlin.math.max

class Y2021Day21Test : BaseTest<Aoc2021D21.GameState>(2021, 21) {

    @Test
    fun part1() {
        val (p1, p2) = getInput()
        val res = Aoc2021D21.part1(p1, p2)
        assertNumber(1067724, res)
    }

    @Test()
    fun part2() {
        val state = getInput()
        val (c1, c2) = Aoc2021D21.countUniverses(state)
        assertNumber(630947104784464L, max(c1, c2))
    }

    override fun parseInput(input: String) = Aoc2021D21.GameState(Aoc2021D21.Player(5), Aoc2021D21.Player(8))
}
