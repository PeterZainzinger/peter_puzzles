package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines

class Y2020Day04Test : BaseTest<List<Aoc2020D04.Passport>>(2020, 4) {

    override fun parseInput(input: String) = input.splitLines().joinToString("\n").split("\n\n")
        .map { line -> line.replace("\n", " ") }
        .map { line -> line.split(" ") }
        .map { keyPairs ->
            keyPairs.map { pairData ->
                val splitted = pairData.split(":")
                splitted.first() to splitted[1]
            }.toMap()
        }
        .map { Aoc2020D04.Passport(it) }

    @Test
    fun part1() {
        val input = getInput()
        val res = input.filter { it.valid() }.size
        assertNumber(213, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = input.filter { it.validStrict() }.size
        assertNumber(147, res)
    }
}
