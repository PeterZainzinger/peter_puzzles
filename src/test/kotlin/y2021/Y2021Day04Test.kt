package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.Point3
import shared.splitLines
import y2021.Aoc2021D01

class Y2021Day04Test : BaseTest<Pair<List<Int>, List<Aoc2021D04.Board>>>(2021, 4) {

    @Test
    fun part1() {
        val (draw, boards) = getInput()
        val state = Aoc2021D04.getFirstWinningBoard(boards, draw)
        assertNumber(58838, state.score())
    }

    @Test
    fun part2() {
        val (draw, boards) = getInput()
        val state = Aoc2021D04.getLastWinningBoard(boards, draw)
        assertNumber(6256, state.score())
    }


    override fun parseInput(input: String): Pair<List<Int>, List<Aoc2021D04.Board>> {

        val lines = input.splitLines()
        val draw = lines.first().split(",").map { it.toInt() }

        val boards = lines.subList(1, lines.size)
            .filter { !it.isBlank() }
            .mapIndexed { index, s -> index to s }
            .groupBy { (it.first / (5)) }
            .mapValues { it.value.map { it.second.split(" ").filter { it.isNotBlank() }.map { it.toInt() } } }
            .entries
            .map { Aoc2021D04.Board(it.value) }

        return draw to boards

    }
}
