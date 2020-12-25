package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines

class Y2020Day13Test : BaseTest<Pair<Int, List<Aoc2020D13.ScheduleItem>>>(2020, 13) {

    override fun parseInput(input: String): Pair<Int, List<Aoc2020D13.ScheduleItem>> {
        val (arrivalText, scheduleText) = input.splitLines()
        val arrival = arrivalText.toInt()
        val schedule = scheduleText.split(",").map {
            when (it) {
                "x" -> Aoc2020D13.ScheduleItem.Placeholder
                else -> Aoc2020D13.ScheduleItem.Bus(it.toInt())
            }
        }
        return arrival to schedule
    }

    @Test
    fun part1() {
        val (arrival, schedule) = getInput()
        val res = Aoc2020D13.part1(arrival, schedule)
        assertNumber(261, res)
    }

    @Test
    fun part2() {
        val (_, schedule) = getInput()
        val res = Aoc2020D13.part2(schedule)
        assertNumber(807435693182510L, res)
    }
}
