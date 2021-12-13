package y2021

import BaseTest
import assertNumber
import assertString
import org.junit.jupiter.api.Test
import shared.Point
import shared.Point3
import shared.splitLines
import y2021.Aoc2021D01

class Y2021Day13Test : BaseTest<Aoc2021D13.Input>(2021, 13) {

    @Test
    fun part1() {
        val input = getInput()
        val res = Aoc2021D13.foldInstruction(input.grid, input.instructions.first())
        assertNumber(666, res.size)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = Aoc2021D13.foldAll(input)
        val p = Aoc2021D13.printGrid(res)
        val assertTo = ".##....##.#..#..##..####.#..#.#..#.#..#\n" +
                "#..#....#.#..#.#..#....#.#..#.#.#..#..#\n" +
                "#.......#.####.#..#...#..####.##...#..#\n" +
                "#.......#.#..#.####..#...#..#.#.#..#..#\n" +
                "#..#.#..#.#..#.#..#.#....#..#.#.#..#..#\n" +
                ".##...##..#..#.#..#.####.#..#.#..#..##."
        assertString(assertTo, p)
    }

    override fun parseInput(input: String) =
        Aoc2021D13.Input.parse(input)
}
