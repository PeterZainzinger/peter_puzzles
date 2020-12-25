package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitLines
import y2020.Aoc2020D25.f
import y2020.Aoc2020D25.searchLoopSize

class Y2020Day25Test : BaseTest<Pair<Long, Long>>(2020, 25) {

    override fun parseInput(input: String): Pair<Long, Long> {
        val (l1, l2) = input.splitLines().map { it.toLong() }
        return l1 to l2
    }

    @Test
    fun part1() {
        val (cardPublicKey, doorPublicKey) = getInput()
        val doorLoop = searchLoopSize(doorPublicKey, 7)
        val cardLoop = searchLoopSize(cardPublicKey, 7)
        assertNumber(16902792, f(doorPublicKey, cardLoop))
    }
}
