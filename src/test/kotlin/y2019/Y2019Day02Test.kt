package y2019

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import y2019.Aoc2019D02.executeIntCode

class Y2019Day02Test : BaseTest<List<Int>>(2019, 2) {

    override fun parseInput(input: String) = input.split(",").map { it.toInt() }

    @Test
    fun part1() {
        val input = getInput()
        val res = executeIntCode(
            input.toMutableList().apply {
                set(1, 12)
                set(2, 2)
            }
        ).first()
        assertNumber(3562624, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val (noun, verb) = findOutput(19690720, input)
        val res = 100 * noun + verb
        assertNumber(8298, res)
    }

    private fun findOutput(target: Int, program: List<Int>): Pair<Int, Int> {
        for (i in 0..99) {
            for (j in 0..99) {
                val res = executeIntCode(
                    program.toMutableList().apply {
                        set(1, i)
                        set(2, j)
                    }
                ).first()
                if (res == target) {
                    return i to j
                }
            }
        }
        throw Exception("not found")
    }
}
