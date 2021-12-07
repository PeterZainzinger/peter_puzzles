package y2021

import kotlin.math.abs

object Aoc2021D07 {
    fun findLowestCost1(input: List<Int>): Pair<Int, Int> {
        val inputOpti = input.groupBy { it }.mapValues { it.value.size }
        return inputOpti.keys.map { option ->
            val cost =
                inputOpti.filterKeys { it != option }.entries.sumOf { abs(option - it.key) * it.value }
            option to cost
        }.minByOrNull { it.second }!!
    }


    fun findLowestCost2(input: List<Int>): Pair<Int, Int> {
        val inputOpti = input.groupBy { it }.mapValues { it.value.size }
        fun steps(n: Int): Int {
            return n + when (n) {
                0 -> 0
                else -> steps(n - 1)
            }
        }
        val min = inputOpti.keys.minByOrNull { it }!!
        val max = inputOpti.keys.maxByOrNull { it }!!
        val stepsMap = (min..max).associateWith { steps(it) }
        return inputOpti.keys.map { option ->
            val cost =
                inputOpti.filterKeys { it != option }.entries.sumOf { stepsMap[abs(option - it.key)]!! * it.value }
            option to cost
        }.minByOrNull { it.second }!!
    }

}

