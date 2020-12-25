package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines

class Y2020Day11Test : BaseTest<SeatWorld>(2020, 11) {

    override fun parseInput(input: String) = input.splitLines().map {
        it.toCharArray().map {
            when (it) {
                'L' -> Aoc2020D11.SeatState.Empty
                '.' -> Aoc2020D11.SeatState.Floor
                else -> throw Exception("what is this $it")
            }
        }
    }

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2020D11.countSeated(Aoc2020D11.untilStable(input))
        assertNumber(2359, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2020D11.countSeated(Aoc2020D11.untilStable2(input))
        assertNumber(2131, res)
    }
}
