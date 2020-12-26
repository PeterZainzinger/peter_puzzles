package y2020

import shared.chineseRemainder
import shared.gcdExt
import shared.lcm

object Aoc2020D13 {
    sealed class ScheduleItem {
        object Placeholder : ScheduleItem()
        data class Bus(val loopTime: Int) : ScheduleItem()
    }

    fun part1(arrival: Int, schedule: List<ScheduleItem>): Int {
        val scheduleWithHoles = schedule.map {
            when (it) {
                is ScheduleItem.Bus -> it.loopTime
                else -> null
            }
        }
        val scheduleTimes = scheduleWithHoles.filterNotNull()

        val (busId, offset) = scheduleTimes
            .map { interval ->
                interval to interval - (arrival % interval)
            }
            .minBy { it.second }!!
        return busId * offset
    }

    fun part2(schedule: List<ScheduleItem>): Long {
        val scheduleWithHoles = schedule.map {
            when (it) {
                is ScheduleItem.Bus -> it.loopTime
                else -> null
            }
        }
        val timesWithOffsets = scheduleWithHoles.mapIndexed { index, i ->
            when (i) {
                null -> null
                else -> -index to i
            }
        }.filterNotNull()
        return chineseRemainder(timesWithOffsets)
    }
}
