package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines
import y2020.Aoc2020D14.applyLine
import y2020.Aoc2020D14.applyLine2

class Y2020Day14Test : BaseTest<List<Aoc2020D14.MemInstruction>>(2020, 14) {

    override fun parseInput(input: String) = input.splitLines().map { line ->
        when {
            line.startsWith("mask =") -> Aoc2020D14.MemInstruction.Mask(line.substring(7))
            line.startsWith("mem") -> {
                val addr = line.subSequence(line.indexOf("[") + 1, line.indexOf("]")).toString().toLong()
                val value = line.substring(line.indexOf("=") + 2).toLong()
                Aoc2020D14.MemInstruction.MemAssign(addr, value)
            }
            else -> throw Exception("not handled $line")
        }
    }

    @Test
    fun part1() {
        val input = getInput()
        val startState = Aoc2020D14.MemState(currentMask = Aoc2020D14.MemInstruction.Mask(""))
        val endState = input.fold(startState, ::applyLine)
        val res = endState.memory.values.sum()
        assertNumber(17028179706934L, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val startState = Aoc2020D14.MemState(currentMask = Aoc2020D14.MemInstruction.Mask(""))
        val endState = input.fold(startState, ::applyLine2)
        val res = endState.memory.values.sum()
        assertNumber(3683236147222L, res)
    }
}
