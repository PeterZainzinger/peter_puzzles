package y2021

import shared.splitLines

object Aoc2021D14 {

    data class Input(val start: String, val rules: Map<String, String>) {
        companion object {
            fun parse(input: String): Input {
                val (start, dirs) = input.split("\n\n")
                val rules = dirs.splitLines().map {
                    val (from, to) = it.split(" -> ")
                    from to to
                }.toMap()
                return Input(start, rules)
            }
        }
    }

    data class IterationState(val chars: Map<String, Long>, val gaps: Map<String, Long>) {
        companion object {
            fun fromInput(input: Input): IterationState {
                val chars = input.start.splitChars().groupBy { it }.mapValues { it.value.size.toLong() }
                val gaps = input.start.windowed(2, 1, false).groupBy { it }.mapValues { it.value.size.toLong() }
                return IterationState(chars, gaps)
            }
        }

        fun iterate(rules: Map<String, String>): IterationState {
            val newChars = gaps.mapNotNull { rules[it.key]?.to(it.value) }.groupBy { it.first }.mapValues { it.value.sumOf { it.second } }
            val newGaps = gaps.entries.flatMap { (gap, count) ->
                when (val entry = rules[gap]) {
                    null -> listOf(gap)
                    else -> listOf(gap.first().toString() + entry, entry + gap.last().toString())
                }.map { it to count }
            }.groupBy { it.first }.mapValues { it.value.map { it.second }.sum() }
            return copy(chars = mapAdd(chars, newChars), gaps = newGaps)
        }
    }


    fun iterate(input: Input,steps: Int): Long {
        var state = IterationState.fromInput(input)
        repeat(steps) {
            state = state.iterate(input.rules)
        }
        val min = state.chars.entries.minByOrNull { it.value }?.value!!
        val max = state.chars.entries.maxByOrNull { it.value }?.value!!
        return max - min

    }

    fun mapAdd(a: Map<String, Long>, b: Map<String, Long>): Map<String, Long> =
        (a.keys + b.keys).map { key -> key to ((a[key] ?: 0L) + (b[key] ?: 0L)) }.toMap()


}


