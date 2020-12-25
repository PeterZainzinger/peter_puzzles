object Aoc2019D04 {

    fun searchPasswordInRange(range: IntRange): List<Int> =
        range
            .map { it to "$it".toCharArray().toList() }
            .filter { (_, chars) ->
                // Two adjacent digits are the same (like 22 in 122345).
                val hasAny = chars.mapIndexed { index, v ->
                    when (index) {
                        0 -> false
                        else -> v == chars[index - 1]
                    }
                }
                hasAny.any { it }
            }
            .filter { (_, chars) ->
                val digits = chars.map { it.toInt() }
                // Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).
                digits.foldIndexed(
                    true,
                    { index, before, digit ->
                        when (index) {
                            0 -> before
                            else -> before && digits[index - 1] <= digit
                        }
                    }
                )
            }
            .map { it.first }

    fun searchMore(items: List<Int>) = items
        .map { it to "$it".toCharArray().toList() }
        .filter { (_, chars) ->
            val digits = chars.map { it.toInt() }
            // calc chain lengths
            digits.foldIndexed(
                listOf<Int>(),
                { index, acc, digit ->
                    val before = when (index) {
                        0 -> null
                        else -> digits[index - 1]
                    }
                    val beforeCount = acc.lastOrNull() ?: 0
                    when (digit) {
                        before -> acc.subList(0, acc.size - 1) + listOf(beforeCount + 1)
                        else -> acc + listOf(1)
                    }
                }
            ).any { it == 2 }
        }
        .map { it.first }
}
