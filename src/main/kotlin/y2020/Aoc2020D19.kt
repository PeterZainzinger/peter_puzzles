package y2020

object Aoc2020D19 {
    sealed class Rule(open val index: Int) {
        data class Simple(
            override val index: Int,
            val char: Char,
        ) : Rule(index)

        data class Options(
            override val index: Int,
            val options: List<List<Int>>,
        ) : Rule(index)
    }
}
