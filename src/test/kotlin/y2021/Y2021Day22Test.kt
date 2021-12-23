package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point3
import shared.splitLines
import kotlin.math.max

class Y2021Day22Test : BaseTest<List<Aoc2021D22.Instruction>>(2021, 22) {

    @Test
    fun part1() {
        val instructions = getInput()
        val res = Aoc2021D22.part1(instructions)
        assertNumber(570915, res)
    }

    @Test()
    fun part2() {
        val instructions = getInput()
        val res = Aoc2021D22.part2(instructions)
        assertNumber(1268313839428137, res)
    }

    override fun parseInput(input: String) = input.splitLines().map { Aoc2021D22.Instruction.parse(it) }
}
