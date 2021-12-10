package y2021

import java.lang.Exception

object Aoc2021D10 {

    fun part1(lines: List<String>): Int = lines.mapNotNull { getInvalidChar(it) }.map { errors[it]!! }.sum()
    fun part2(lines: List<String>): Long {
        val incompleteLines = lines.filter { getInvalidChar(it) == null }
        val scores = incompleteLines.map { getClosing(it) }.map { getScore(it) }.sorted()
        return scores[scores.size / 2]

    }

    private val errors = mapOf(
        ")" to 3,
        "]" to 57,
        "}" to 1197,
        ">" to 25137,
    )

    private val errorsClosing = mapOf(
        ")" to 1,
        "]" to 2,
        "}" to 3,
        ">" to 4,
    )


    private val mapping = mapOf(
        "(" to ")",
        "[" to "]",
        "{" to "}",
        "<" to ">",
    )

    private val mappingInverse = mapping.entries.map { it.value to it.key }.toMap()
    private val openingChars = mapping.keys.toSet()

    private fun getInvalidChar(input: String): String? {
        val chars = input.splitChars()
        val stack = mutableListOf<String>()
        chars.forEachIndexed { index, c ->
            when (openingChars.contains(c)) {
                true -> stack.add(c)
                false -> {
                    val openingChar = mappingInverse[c]
                    val last = stack.last()
                    when (last) {
                        // valid case
                        openingChar -> {
                            stack.removeLast()
                        }
                        // invalid
                        else -> {
                            return c
                            // do nothing else
                        }
                    }
                }
            }
        }
        return null
    }


    private fun getClosing(input: String): List<String> {
        val chars = input.splitChars()
        val stack = mutableListOf<String>()
        chars.forEachIndexed { index, c ->
            when (openingChars.contains(c)) {
                true -> stack.add(c)
                false -> {
                    val openingChar = mappingInverse[c]
                    val last = stack.last()
                    when (last) {
                        // valid case
                        openingChar -> {
                            stack.removeLast()
                        }
                        else -> {
                            throw  Exception("wtf")
                        }
                    }
                }
            }
        }
        return stack.reversed().map { mapping[it]!! }
    }

    private fun getScore(items: List<String>): Long = items.fold(0L) { a, c ->
        a * 5L + (errorsClosing[c]!!).toLong()
    }


}

