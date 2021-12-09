package y2021

fun String.splitChars() = split("").filter { it.isNotBlank() }

object Aoc2021D08 {


    data class Line(val signals: List<String>, val output: List<String>) {
        companion object {
            fun parse(input: String): Line {
                val (f, s) = input.split(" | ")
                return Line(f.split(" "), s.split(" "))
            }
        }
    }

    private val alphabet = "abcdefg"
    private val alphabetChars = alphabet.split("").filter { it.isNotBlank() }

    private val mappingReal = mapOf(
        0 to "abcefg",
        1 to "cf",
        2 to "acdeg",
        3 to "acdfg",
        4 to "bcdf",
        5 to "abdfg",
        6 to "abdefg",
        7 to "acf",
        8 to "abcdefg",
        9 to "abcdfg",
    )

    private val mappingInverse: Map<Set<String>, Int> =
        mappingReal.entries.map { it.value.splitChars().toSet() to it.key }.toMap()
    private val allCombinations = mappingReal.values.toList().map { it.splitChars().toSet() }.toSet()


    private fun generatePermutations(
        fixed: String,
        remaining: String = ""
    ): List<String> = when (remaining) {
        "" -> listOf(fixed)
        else -> remaining.split("").filter { it.isNotBlank() }.flatMap { fixedChar ->
            val newRemaining = remaining.replace(fixedChar, "")
            generatePermutations(fixed + fixedChar, newRemaining)
        }
    }

    fun solvePart2(lines: List<Line>): Long {

        val allOptions = generatePermutations("", alphabet)

        val mappedOptions = allOptions.map { option ->
            alphabetChars.zip(option.split("").filter { it.isNotBlank() }).toMap()
        }

        return lines.sumOf {
            val ro = mappedOptions.filter { option ->
                it.signals.all { signal ->
                    val mappedSignal = signal.splitChars().map { option[it] }.joinToString("")
                    val contained = allCombinations.contains(mappedSignal.splitChars().toSet())
                    contained
                }
            }
            assert(ro.size == 1)
            val option = ro.first()

            val digits = it.output.map { o ->
                val realOutput = o.splitChars().map { option[it]!!.toString() }.toSet()
                mappingInverse
                val d = mappingInverse[realOutput]
                d
            }.joinToString("")
            digits.toLong()
        }

    }

}

