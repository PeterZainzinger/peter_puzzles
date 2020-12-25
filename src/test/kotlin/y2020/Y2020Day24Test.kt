package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines

class Y2020Day24Test : BaseTest<List<List<Aoc2020D24.DirectionInstruction>>>(2020, 24) {

    override fun parseInput(input: String) = input.splitLines().map { line ->
        val res = mutableListOf<Aoc2020D24.DirectionInstruction>()
        var remaining = line
        while (remaining.isNotEmpty()) {
            val d = Aoc2020D24.DirectionInstruction.all.first { remaining.startsWith(it.prefix) }
            res.add(d)
            remaining = remaining.substring(d.prefix.length)
        }
        res.toList()
    }

    @Test
    fun part1() {
        val input = getInput()
        val state = Aoc2020D24.buildState(input)
        val res = state.values.filter { it }.size
        assertNumber(386, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val initial = Aoc2020D24.buildState(input)
        val state = Aoc2020D24.gameOfLife(initial, 100)
        val res = state.values.filter { it }.size
        assertNumber(4214, res)
    }
}
