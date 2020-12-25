package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import shared.splitLines

class Y2020Day21Test : BaseTest<List<Aoc2020D21.Food>>(2020, 21) {

    override fun parseInput(input: String) = input.splitLines().map {
        val (i, a) = it.split("(contains")
        val ingridients = i.trim().split(" ").toSet()
        val allergens = a.replace(")", "").split(",").map { it.trim() }.toSet()
        Aoc2020D21.Food(ingridients, allergens)
    }

    @Test
    fun part1() {
        val input = getInput()
        val result = Aoc2020D21.solveFoods(input)
        val allIngriedients = input.flatMap { it.ingridients }.toSet()
        val safeFoods = allIngriedients.subtract(result.values)
        val res = safeFoods.map { food ->
            input.map {
                when (it.ingridients.contains(food)) {
                    true -> 1
                    false -> 0
                }
            }.sum()
        }.sum()
        assertNumber(2282, res)
    }

    @Test
    fun part2() {
        val input = getInput()
        val result = Aoc2020D21.solveFoods(input)
        val res = result.map { it.key to it.value }.sortedBy { it.first }.map { it.second }.joinToString(",")
        assertEquals("vrzkz,zjsh,hphcb,mbdksj,vzzxl,ctmzsr,rkzqs,zmhnj", res)
    }
}
