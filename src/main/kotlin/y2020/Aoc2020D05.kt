package y2020

object Aoc2020D05 {
    data class Line(val rowsCode: String, val columnCode: String) {

        val rowDigits = rowsCode.map {
            when (it) {
                'F' -> 0
                'B' -> 1
                else -> throw RuntimeException("invalid char $it")
            }
        }
        val columnDigits = columnCode.map {
            when (it) {
                'L' -> 0
                'R' -> 1
                else -> throw RuntimeException("invalid char $it")
            }
        }

        fun seatID() = rowDigits.toInt() * 8 + columnDigits.toInt()
    }

    private fun List<Int>.toInt() =
        Integer.parseInt(joinToString(""), 2)


    fun exercise1(input: List<Line>) =
        input.map { it.seatID() }.maxOrNull()

    fun exercise2(input: List<Line>): Set<Int> {
        val allIds = input.map { it.seatID() }
        val min = allIds.min()!!
        val max = allIds.max()!!
        return (min..max).toSet().minus(allIds)
    }


}


