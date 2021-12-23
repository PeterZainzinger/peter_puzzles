package y2021

import shared.*


object Aoc2021D22 {

    fun LongRange.contains(other: LongRange) = this.first <= other.first && other.last <= this.last
    fun LongRange.copy(first: Long? = null, last: Long? = null) = (first ?: this.first)..(last ?: this.last)
    fun LongRange.numberOfBlock() = (this.last - this.first) + 1
    fun LongRange.intersect(other: LongRange): LongRange? = when {
        this.contains(other) -> other
        other.contains(this) -> this
        // overlap start
        first in other -> first..other.last
        // overlap end
        last in other -> other.first..last
        else -> null
    }


    data class Instruction(val on: Boolean, val cubical: Cubical) {
        companion object {
            private fun extractPart(input: String): LongRange {
                val (_, range) = input.split("=")
                val (start, end) = range.split("..")
                return start.toLong()..end.toLong()
            }

            fun parse(line: String): Instruction {
                val (dir, rest) = line.split(" ")
                val (xP, yP, zP) = rest.split(",")
                val cub = Cubical(extractPart(xP), extractPart(yP), extractPart(zP))
                return Instruction(dir == "on", cub)
            }
        }
    }


    data class Cubical(val x: LongRange, val y: LongRange, val z: LongRange) {

        companion object {
            val initalRange = Cubical(-50L..50L, -50L..50L, -50L..50L)

        }

        val volume
            get() = x.numberOfBlock() * y.numberOfBlock() * z.numberOfBlock()

        private val isValid: Boolean
            get() = x.numberOfBlock() > 0 && y.numberOfBlock() > 0 && z.numberOfBlock() > 0

        private infix fun intersect(other: Cubical): Cubical? {
            val xValue = x.intersect(other.x) ?: return null
            val yValue = y.intersect(other.y) ?: return null
            val zValue = z.intersect(other.z) ?: return null
            val o = Cubical(xValue, yValue, zValue)
            return when (o.isValid) {
                true -> o
                false -> null
            }
        }

        fun contains(other: Cubical): Boolean =
            x.contains(other.x) && x.contains(other.y) && z.contains(other.z)


        operator fun minus(other: Cubical): List<Cubical> {
            val intersection = this intersect other
            if (intersection == null) return listOf(this)
            else if (intersection == this) return emptyList()
            val toReturn = mutableListOf<Pair<Int, Cubical>>()
            // 1
            if (x.first < intersection.x.first) {
                toReturn.add(1 to this.copy(x = x.copy(last = intersection.x.first - 1)))
            }
            // 2
            if (x.last > intersection.x.last) {
                toReturn.add(2 to this.copy(x = x.copy(first = intersection.x.last + 1)))
            }
            // 3
            if (y.first < intersection.y.first) {
                toReturn.add(
                    3 to
                            this.copy(
                                x = intersection.x,
                                y = y.copy(last = intersection.y.first - 1)
                            )
                )
            }
            // 4
            if (y.last > intersection.y.last) {
                toReturn.add(
                    4 to
                            this.copy(
                                x = intersection.x,
                                y = y.copy(first = intersection.y.last + 1)
                            )
                )
            }
            // 5
            if (z.first < intersection.z.first) {
                toReturn.add(
                    5 to
                            intersection.copy(
                                z = z.first until intersection.z.first
                            )
                )
            }
            // 6
            if (z.last > intersection.z.first) {
                toReturn.add(
                    6 to
                            intersection.copy(
                                z = (intersection.z.last + 1..z.last)
                            )
                )
            }
            return toReturn.map { it.second }
        }
    }


    operator fun List<Cubical>.minus(cuboid: Cubical) = flatMap { it - cuboid }

    private fun applyInstructions(input: List<Instruction>): List<Cubical> = input.fold(listOf()) { acc, instruction ->
        (acc - instruction.cubical) + when (instruction.on) {
            true -> listOf(instruction.cubical)
            false -> emptyList()
        }
    }


    fun part1(input: List<Instruction>) =
        applyInstructions(input.filter { Cubical.initalRange.contains(it.cubical) }).sumOf { it.volume }

    fun part2(input: List<Instruction>) = applyInstructions(input).sumOf { it.volume }


}


