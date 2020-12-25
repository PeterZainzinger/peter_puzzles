package y2019

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitComma
import shared.splitLines

class Y2019Day03Test : BaseTest<List<List<Aoc2019D03.WireEdge>>>(2019, 3) {

    override fun parseInput(input: String) = input.splitLines().map {
        it.splitComma().map {
            parseWireEdge(it)
        }
    }

    private fun parseWireEdge(input: String): Aoc2019D03.WireEdge {
        val value = Integer.parseInt(input.substring(1 until input.length))
        return when (input.first()) {
            'L' -> Aoc2019D03.WireEdge(-1, 0, value)
            'R' -> Aoc2019D03.WireEdge(1, 0, value)
            'U' -> Aoc2019D03.WireEdge(0, 1, value)
            'D' -> Aoc2019D03.WireEdge(0, -1, value)
            else -> throw Exception("what to do with $input")
        }
    }

    @Test
    fun part1() {
        val (inputA, inputB) = getInput()
        val closesPoint = Aoc2019D03.closesIntersectionPoint(inputA, inputB)
        assertNumber(865, closesPoint.manhattenDistanceToZero)
    }

    @Test
    fun part2() {
        val (inputA, inputB) = getInput()
        val closesPointEnergy = Aoc2019D03.energyEffiecientPoint(inputA, inputB)
        assertNumber(35038, closesPointEnergy)
    }
}
