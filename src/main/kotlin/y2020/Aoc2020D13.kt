package y2020

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
                else -> i to index
            }
        }.filterNotNull()
        // PART 2
        // https://de.wikipedia.org/wiki/Chinesischer_Restsatz ftw!
        val M = timesWithOffsets.fold(1L, { acc, new -> lcm(acc, (new.first).toLong()) })
        val res = timesWithOffsets.map { (m_i, a_i) ->
            val M_i = M / m_i
            val (_, r_i, s_i) = gcdExt(m_i.toLong(), M_i)
            val e_i = s_i * M_i
            a_i.toLong() * e_i
        }.sum()
        var resmod = res
        if (res < 0) {
            while (resmod + M < 0) {
                resmod += M
            }
        } else {
            while (resmod > 0) {
                resmod -= M
            }
        }
        return -resmod
    }
}
