package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines

class Y2020Day02Test : BaseTest<List<Aoc2020D02.PasswordLine>>(2020, 2) {

    override fun parseInput(input: String) = input.splitLines().map { input ->
        val splitted = input.split(":")
        val firstSplitted = splitted[0].split(" ")
        val numbersSplitted = firstSplitted[0].split("-")
        Aoc2020D02.PasswordLine(
            start = Integer.parseInt(numbersSplitted[0]),
            end = Integer.parseInt(numbersSplitted[1]),
            letter = firstSplitted[1].toCharArray().first(),
            //password = splitted[1].strip()
            password = splitted[1]
        )
    }

    @Test
    fun part1() {
        val input = getInput()
        val res = input.filter { it.isValid() }.size
        assertNumber(416, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = input.filter { it.isValidOther() }.size
        assertNumber(688, res)
    }
}
