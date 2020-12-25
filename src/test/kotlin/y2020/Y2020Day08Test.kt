package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines
import y2020.Aoc2020D08.execute
import y2020.Aoc2020D08.Instruction
import y2020.Aoc2020D08.ProgramState

class Y2020Day08Test : BaseTest<List<Instruction>>(2020, 8) {

    override fun parseInput(input: String) = input.splitLines().map { line ->
        when (val start = line.substring(0..2)) {
            "nop" -> Instruction.NoOp(Integer.parseInt(line.substring(4 until line.length)))
            "acc" -> Instruction.Acc(Integer.parseInt(line.substring(4 until line.length)))
            "jmp" -> Instruction.Jump(Integer.parseInt(line.substring(4 until line.length)))
            else -> throw Exception("start $start cant be used")
        }
    }

    @Test
    fun part1() {
        val input = getInput()
        val res = execute(input, ProgramState()).acc
        assertNumber(2034, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val altPrograms = input.mapIndexed { index, instruction ->
            when (instruction) {
                is Instruction.Jump -> input.toMutableList()
                    .apply { set(index, Instruction.NoOp(instruction.jumpToLine)) }
                is Instruction.NoOp -> input.toMutableList().apply { set(index, Instruction.Jump(instruction.value)) }
                else -> null
            }
        }.filterNotNull()
        val targetProgram = altPrograms.first { execute(it, ProgramState()).result == Aoc2020D08.ProgramResult.done }
        val res = execute(targetProgram, ProgramState()).acc
        assertNumber(672, res)
    }

}