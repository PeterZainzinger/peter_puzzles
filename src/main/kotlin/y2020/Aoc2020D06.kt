package y2020

import shared.alphabet

object Aoc2020D06 {

    private fun charactersOfAnswers(
        input: List<List<String>>,
        initial: Set<Char>,
        operation: (Set<Char>, Set<Char>) -> Set<Char>
    ) =
        input.map { items ->
            items.map { it.toCharArray().toSet() }.fold(initial = initial, operation = operation).size
        }.sum()

    fun exercise1(input: List<List<String>>) = charactersOfAnswers(input, emptySet()) { a, b -> a union b }
    fun exercise2(input: List<List<String>>) = charactersOfAnswers(input, alphabet) { a, b -> a intersect b }
}
