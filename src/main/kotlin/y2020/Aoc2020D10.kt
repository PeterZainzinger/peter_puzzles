package y2020

object Aoc2020D10 {
    fun part1(input: List<Int>): Int {
        val internal = input.max()!! + 3
        val inputWithStart = (listOf(0) + input)
        val diffs = inputWithStart.mapIndexed { index, i ->
            when (index) {
                inputWithStart.size - 1 -> 3
                else -> inputWithStart[index + 1] - i
            }
        }
        val count1 = diffs.filter { it == 1 }.size
        val count3 = diffs.filter { it == 3 }.size
        return count1 * count3
    }

    fun countArrangements(input: List<Int>) = allArrangements(
        0,
        options = input + listOf(input.max()!! + 3),
        mutableMapOf()
    )

    private fun allArrangements(last: Int, options: List<Int>, memory: MutableMap<Int, Long>): Long =
        when (val next = options.firstOrNull()) {
            null -> 1
            else -> {
                val inputId = (last to options).hashCode()
                when (val m = memory[inputId]) {
                    null -> {
                        val diff = next - last
                        val res = when {
                            diff < 3 -> {
                                // two options skip or take
                                val skipOption = allArrangements(last, options.subList(1, options.size), memory)
                                val takeOption = allArrangements(next, options.subList(1, options.size), memory)
                                skipOption + takeOption
                            }
                            // we have to take it
                            diff == 3 -> allArrangements(next, options.subList(1, options.size), memory)
                            else -> 0
                        }
                        memory[inputId] = res
                        res
                    }
                    else -> m
                }
            }
        }
}
