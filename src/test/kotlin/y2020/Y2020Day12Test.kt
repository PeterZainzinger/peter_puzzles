package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.splitLines

class Y2020Day12Test : BaseTest<List<Aoc2020D12.MoveInstruction>>(2020, 12) {

    override fun parseInput(input: String) = input.splitLines().map { input ->
        val value = input.substring(1 until input.length).toInt()
        when (val action = input.first()) {
            'N' -> Aoc2020D12.MoveInstruction.Direct(Point(0, 1), value = value)
            'S' -> Aoc2020D12.MoveInstruction.Direct(Point(0, -1), value = value)
            'E' -> Aoc2020D12.MoveInstruction.Direct(Point(1, 0), value = value)
            'W' -> Aoc2020D12.MoveInstruction.Direct(Point(-1, 0), value = value)
            'L' -> Aoc2020D12.MoveInstruction.ChangeDir(value)
            'R' -> Aoc2020D12.MoveInstruction.ChangeDir(360 - value % 360)
            'F' -> Aoc2020D12.MoveInstruction.Forward(value = value)
            else -> throw Exception("invalid dir $action")
        }
    }

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2020D12.calcEndPosition(
            input,
            Point(0, 0),
            Point(1, 0),
            Aoc2020D12::calcNextPosition
        ).manhattenDistanceToZero
        assertNumber(1457, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2020D12.calcEndPosition(
            input,
            Point(0, 0),
            Point(10, 1),
            Aoc2020D12::calcNextPosition2
        ).manhattenDistanceToZero
        assertNumber(106860, res)
    }
}
