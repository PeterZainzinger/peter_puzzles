package y2020

object Aoc2020D07 {

    fun findParents(input: List<Pair<String, List<Pair<Int, String>>>>, search: String): Set<String> {
        val parentsDirectly =
            input.filter { it.second.any { inner -> inner.second == search } }.map { it.first }.toSet()
        val higher = parentsDirectly.flatMap { findParents(input, it) }.toSet()
        return parentsDirectly + higher
    }

    fun countChilds(input: Map<String, List<Pair<Int, String>>>, search: String): Int =
        when (val entry = input[search]) {
            null -> 0
            else -> entry.map {
                it.first * (countChilds(input, it.second) + 1)
            }.sum()
        }
}


