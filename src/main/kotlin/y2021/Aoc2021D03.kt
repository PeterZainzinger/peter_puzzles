package y2021

import shared.Point
import shared.Point3
import java.util.*

object Aoc2021D03 {

    val sample = "110001001000"

    fun calcGammaAndEpsilon(items: List<String>): Int {

        val res = sample.mapIndexed { index, c ->
            val entries = items.map { it.get(index) }.groupBy { it }
            val zeros = entries['0']!!
            val ones = entries['1']!!

            if (zeros.size > ones.size) {
                "0" to "1"
            } else {
                "1" to "0"
            }
        }
        val gamma = res.map { it.first }.joinToString("").toInt(2)
        val epsilon = res.map { it.second }.joinToString("").toInt(2)

        return gamma * epsilon

    }

    fun calcOxygonRating(items: List<String>): Int {
        var currentItems = items.mapIndexed { index, s -> index to s }

        var currentPos = 0
        while (true) {

            val entries = currentItems.map { it.second.get(currentPos) }.groupBy { it }
            val zeros = entries['0']!!
            val ones = entries['1']!!

            val mostCommon = when (zeros.size > ones.size) {
                true -> '0'
                false -> '1'
            }
            currentItems = currentItems.filter { it.second.get(currentPos) == mostCommon }

            if(currentItems.size == 1 ){
                return currentItems.first().second.toInt(2)
            }

            currentPos++
        }


    }

    fun co2(items: List<String>): Int {
        var currentItems = items.mapIndexed { index, s -> index to s }

        var currentPos = 0
        while (true) {

            val entries = currentItems.map { it.second.get(currentPos) }.groupBy { it }
            val zeros = entries['0']!!
            val ones = entries['1']!!

            val mostCommon = when (zeros.size <= ones.size) {
                true -> '0'
                false -> '1'
            }
            currentItems = currentItems.filter { it.second.get(currentPos) == mostCommon }

            if(currentItems.size == 1 ){
                return currentItems.first().second.toInt(2)
            }

            currentPos++
        }

    }

}
