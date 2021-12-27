package y2021

import shared.*
import java.io.File
import java.util.*
import kotlin.math.pow

object Aoc2021D24 {

    data class Exp(val op: String, val left: String, val right: String? = null) {
        companion object {
            fun parse(line: String): Exp {
                val res = line.split(" ")
                return if (res.size == 2) {
                    Exp(res[0], res[1])
                } else {
                    val (o, l, r) = res
                    Exp(o, l, r)
                }
            }
        }
    }

    // manually extracted
    private fun fPart(a1: Int, a2: Int, a3: Boolean, zInput: Int, arg: Int): Int {
        var z = zInput
        var w = arg
        var x = zInput
        x = x % 26
        z = z / when (a3) {
            true -> 26
            false -> 1
        }
        x = x + a1
        x = if (x == w) 1 else 0
        x = if (x == 0) 1 else 0
        var y = 0
        y = y + 25
        y = y * x
        y = y + 1
        z = z * y
        y = y * 0
        y = y + w
        y = y + a2
        y = y * x
        z = z + y
        return z
    }

    private data class MoveTarget(val z: Int, val fixedDigits: List<Int>) {
        fun appendValue(newZTarget: Int, fixedDigit: Int) =
            copy(z = newZTarget, fixedDigits = listOf(fixedDigit) + fixedDigits)
    }

    private fun findMoreOptions(
        chars: List<Triple<Int, Int, Boolean>>,
        input: MoveTarget,
        depth: Int,
        radius: Int
    ): List<MoveTarget> {
        if (depth == 0) {
            return listOf(input).filter { it.z == 0 }
        }
        val (a1, a2, a3) = chars[depth - 1]
        val optionsNew = (0..radius).flatMap { arg ->
            (1..9).mapNotNull { d ->
                val value = fPart(a1, a2, a3, arg, d)
                when (input.z) {
                    value -> arg to d
                    else -> null
                }
            }
        }
        val res = optionsNew.map { input.appendValue(it.first, it.second) }.flatMap {
            findMoreOptions(chars, it, depth - 1, radius)
        }
        return res
    }

    fun search(lines: List<Exp>): Pair<Long,Long>{
        val windows = lines.windowed(18, 18, false)
        val chars = windows.map {
            it[5].right!!.toInt() to it[15].right!!.toInt() then (it[4].right == "26")
        }

        var step = 2
        val time = System.currentTimeMillis()
        val radius = 1000 * 16
        print("search with radius $radius")
        val allOptions = findMoreOptions(
            chars,
            MoveTarget(0, emptyList()),
            14,
            radius = radius
        ).filter { it.fixedDigits.size == 14 }

        val allAnswerNumbers = allOptions.map { it to it.fixedDigits.joinToString("").toLong() }

        val answer = allAnswerNumbers.maxByOrNull { it.second }
        println(answer)
        val smallerst = allAnswerNumbers.minByOrNull { it.second }
        println(smallerst)
        ++step
        val duration = System.currentTimeMillis() - time
        println("took ${duration / 1000}s")

        return answer!!.second to smallerst!!.second
    }

}


