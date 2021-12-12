package y2021

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.Point
import shared.Point3
import shared.splitLines
import y2021.Aoc2021D01

class Y2021Day12Test : BaseTest<Pair<Aoc2021D12.Node, Aoc2021D12.Node>>(2021, 12) {

    @Test
    fun part1() {
        val (start, end) = getInput()
        val res = Aoc2021D12.findPath(listOf(start), end, false).size
        assertNumber(3887, res)
    }

    @Test
    fun part2() {
        val (start, end) = getInput()
        val res = Aoc2021D12.findPath(listOf(start), end, true).size
        assertNumber(104834, res)
    }

    override fun parseInput(input: String) =
        Aoc2021D12.Node.constructNetwork(input.splitLines())
}
