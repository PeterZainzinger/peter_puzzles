package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitEmptyLines
import y2020.Aoc2020D20.buildWholeAndSearch
import y2020.Aoc2020D20.parseTile
import y2020.Aoc2020D20.searchArrangment
import kotlin.math.sqrt

class Y2020Day20Test : BaseTest<List<Aoc2020D20.Tile>>(2020, 20) {

    override fun parseInput(input: String) = input.splitEmptyLines().map {
        val lines = it.trim().split("\n")
        val id = lines.first().substring(5..8).toInt()
        val pixels = parseTile(lines.subList(1, lines.size))
        Aoc2020D20.Tile(id, pixels)
    }

    @Test
    fun part1() {
        val tiles = getInput()
        val width = sqrt(tiles.size.toDouble()).toInt()
        val oneOption = searchArrangment(tiles)
        val res = listOf(
            oneOption.filled[Aoc2020D20.GridPosition(0, 0)]!!.id,
            oneOption.filled[Aoc2020D20.GridPosition(0, width - 1)]!!.id,
            oneOption.filled[Aoc2020D20.GridPosition(width - 1, 0)]!!.id,
            oneOption.filled[Aoc2020D20.GridPosition(width - 1, width - 1)]!!.id,
        ).fold(1L, { a, b -> a * b })
        assertNumber(15003787688423, res)
    }

    @Test
    fun part2() {
        val tiles = getInput()
        val monsterPattern = loadFileWithName("y2020/20_seamonster.txt").trimEnd()
        val res = buildWholeAndSearch(tiles, monsterPattern)
        assertNumber(1705, res)
    }
}
