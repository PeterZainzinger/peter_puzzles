package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import y2020.Aoc2020D23.runRound

class Y2020Day23Test : BaseTest<List<Int>>(2020, 23) {

    override fun parseInput(input: String) = input.toCharArray().map { it.toString() }.map { it.toInt() }

    private fun after1List(list: Aoc2020D23.CircularList): String {
        var target1: Aoc2020D23.CircularList.Node? = list.start
        while (target1!!.value != 1) {
            target1 = target1.next
        }
        var next = target1.next!!
        val res = mutableListOf<Int>()
        while (next != target1) {
            res.add(next.value)
            next = next.next!!
        }
        return res.joinToString("")
    }

    private fun find2AfterProduct(list: Aoc2020D23.CircularList): Long {
        var target1: Aoc2020D23.CircularList.Node? = list.start
        while (target1!!.value != 1) {
            target1 = target1.next
        }
        val f1 = target1.next!!
        val f2 = f1.next!!
        return f1.value.toLong() * f2.value.toLong()
    }

    @Test
    fun part1() {
        val input = getInput()
        Assertions.assertEquals("32658947", after1List(runRound(input, 100)))
    }

    @Test
    fun part2() {
        val inputStart = getInput()
        val inputStartMax = inputStart.maxOrNull()!!
        val input = (inputStart + (inputStartMax + 1..1000000)).toMutableList()
        assertNumber(683486010900L, find2AfterProduct(runRound(input, 10000000)))
    }
}
