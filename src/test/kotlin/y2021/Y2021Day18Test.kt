package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.fold1
import shared.splitLines

class Y2021Day18Test : BaseTest<List<Aoc2021D18.SnailFishNumber>>(2021, 18) {

    @Test
    fun part1() {
        val input = getInput()
        val res = input.fold1 { a, b -> (a+b).reduce() }!!.magnitude()
        assertNumber(4323, res)
    }

    @Test()
    fun part2() {
        val input = getInput()

        val res = input.flatMap { first ->
            input.map { second ->
                when (first) {
                    second -> -1L
                    else -> (first + second).reduce().magnitude()
                }
            }
        }.maxOrNull()!!

        assertNumber(4749, res)
    }


    override fun parseInput(input: String) = input.splitLines().map {
        Aoc2021D18.SnailFishNumber.parse(it)
    }
}
