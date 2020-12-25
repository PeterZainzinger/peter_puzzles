package y2020

import shared.then
import java.lang.RuntimeException

object Aoc2020D01 {

    fun findPair(numbers: List<Int>, target: Int): Pair<Int, Int> {
        for (i in numbers) {
            for (j in numbers) {
                if (i + j == target) {
                    return i to j
                }
            }
        }
        throw RuntimeException("not found")
    }

    fun findTriple(numbers: List<Int>, target: Int): Triple<Int, Int, Int> {
        for (i in numbers) {
            for (j in numbers) {
                for (h in numbers) {
                    if (i + j + h == target) {
                        return i then j then h
                    }
                }
            }
        }
        throw RuntimeException("not found")
    }

}


