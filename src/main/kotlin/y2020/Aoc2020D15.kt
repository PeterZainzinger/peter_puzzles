package y2020

object Aoc2020D15 {
    fun findNth(input: List<Int>, n: Int): Int {
        val currentMem =
            input.toList().mapIndexed { index, i -> i to index }.toMap().toMutableMap()
        var currentNum = input.last()
        var i = input.size
        while (i < n) {
            var newNum = when (val entry = currentMem[currentNum]) {
                null -> 0
                else -> i - entry - 1
            }
            currentMem[currentNum] = i - 1
            currentNum = newNum
            i++
        }
        return currentNum
    }
}
