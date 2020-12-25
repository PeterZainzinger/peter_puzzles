package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines
import y2020.Aoc2020D07.countChilds
import y2020.Aoc2020D07.findParents

class Y2020Day07Test : BaseTest<List<Pair<String, List<Pair<Int, String>>>>>(2020, 7) {

    override fun parseInput(input: String) = input.splitLines().map {
        val colorInfo = it.split(" bags contain ")
        val type = colorInfo[0]
        val content = colorInfo[1].split(",").map {
            when (it) {
                "no other bags." -> 0 to ""
                else -> {
                    val input = it.replace(" bags", "").replace("bag", "").replace(".", "").replace(",", "").trim()
                    val many = Integer.parseInt(input.substring(0..0))
                    val info = input.substring(1 until input.length).trim()
                    many to info
                }
            }
        }
        type to content
    }

    @Test
    fun part1() {
        val input = getInput()
        val res = findParents(input, "shiny gold").size
        assertNumber(115, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val res = countChilds(input.toMap(), "shiny gold")
        assertNumber(1250, res)
    }
}
