package y2019

object Aoc2019D2 {

    fun executeIntCode(input: List<Int>): List<Int> {
        var currentIndex = 0;
        val currentState = input.toMutableList()
        while (currentIndex < input.size - 4) {
            val op = currentState[currentIndex]
            val x = currentState[currentState[currentIndex + 1]]
            val y = currentState[currentState[currentIndex + 2]]
            val target = currentState[currentIndex + 3]
            val res = when (op) {
                1 -> x + y
                2 -> x * y
                99 -> return currentState
                else -> throw Exception("unknown op $op")
            }
            currentState[target] = res
            currentIndex += 4
        }
        return currentState
    }
}



