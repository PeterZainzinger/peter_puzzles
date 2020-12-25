package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test

class Y2020Day19Test : BaseTest<Pair<List<String>, List<Aoc2020D19.Rule>>>(2020, 19) {

    private fun isValidMessage(rule: Regex, msg: String) = rule.matches(msg)

    override fun parseInput(input: String): Pair<List<String>, List<Aoc2020D19.Rule>> {
        val (rulesText, text) = input.split("\n\n")
        val messages = text.split("\n")
        val isChar = "([0-9]+): \"([a-z])\"".toRegex()
        val refRules = "([0-9]+):".toRegex()
        val rules = rulesText.split("\n").map {
            // println(it)
            when (val f = isChar.find(it)) {
                null -> {
                    val index = refRules.find(it)!!.groups[1]!!.value.toInt()
                    val options = it.split(":")[1]!!.trim().split("|").map {
                        it.trim().split(" ").map { it.toInt() }
                    }
                    Aoc2020D19.Rule.Options(index, options)
                }
                else -> {
                    Aoc2020D19.Rule.Simple(f.groups[1]!!.value.toInt(), f.groups[2]!!.value.toCharArray().first())
                }
            }
        }
        return messages to rules
    }

    @Test
    fun part2() {
        val (messages, rules) = getInput()
        val rulesMap = rules.map { it.index to it }.toMap()
        val rulesStrings = mutableMapOf<Int, String>()

        fun resolveAndPut(rule: Aoc2020D19.Rule, parents: Set<Aoc2020D19.Rule>, loop: Int): String {
            if (loop > 10) {
                throw Exception("loop")
            }
            val loopAdd = when (parents.contains(rule)) {
                true -> 1
                false -> 0
            }
            val entry = rulesStrings[rule.index]
            if (entry != null) {
                return entry
            }
            val res = when (rule) {
                is Aoc2020D19.Rule.Simple -> rule.char.toString()
                is Aoc2020D19.Rule.Options -> {
                    "(" + rule.options.mapNotNull {
                        try {
                            it.map { resolveAndPut(rulesMap[it]!!, parents + setOf(rule), loop + loopAdd) }
                                .joinToString("")
                        } catch (e: Exception) {
                            null
                        }
                    }.joinToString(separator = "|") { "($it)" } + ")"
                }
            }
            rulesStrings[rule.index] = res
            return res
        }

        val res = messages.filter { isValidMessage(resolveAndPut(rulesMap[0]!!, emptySet(), 0).toRegex(), it) }.count()
        assertNumber(329, res)
    }
}
