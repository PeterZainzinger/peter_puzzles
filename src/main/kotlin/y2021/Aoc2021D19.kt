package y2021

import shared.Matrix3
import shared.Point3
import shared.then


object Aoc2021D19 {
    data class Scanner(val index: Int, val scans: List<Point3>) {
        fun rotateEntries(matrix3: Matrix3) = scans.map { matrix3 * it }
    }


    private fun genPermutations(length: Int, current: List<List<Int>>): List<List<Int>> = when (length) {
        0 -> current
        else -> genPermutations(length - 1, current.flatMap { line ->
            (-1..1).map {
                line.toMutableList().apply { add(it) }
            }
        })
    }

    // pure gold :)
    private val allRotationMatrixes = genPermutations(9, listOf(emptyList())).map { Matrix3(it.windowed(3, 3, false)) }
        .filter { it.det == 1 && it * it.transpose() == Matrix3.I }


    fun solve(input: List<Scanner>): Pair<Set<Point3>, Int?> {

        data class Overlap(
            val e1: Point3,
            val v2: Point3,
            val vec: Point3,
            val r2: Matrix3,
        )



        var sourceScanner = input[0]

        // Grow the scanner, so no need to invert Matrices etc

        val includedScanners = listOf(sourceScanner).toMutableList()
        val scannerLocations = listOf(Point3.zero).toMutableList()
        while ((input.toSet() - includedScanners.toSet()).isNotEmpty()) {
            val remainingScanners = (input.toSet() - includedScanners.toSet())
            val newRot = allRotationMatrixes.flatMap { m -> remainingScanners.map { it to m } }
                .firstNotNullOfOrNull { (toTryScanner, r2) ->
                    val entries1 = sourceScanner.scans
                    val entries2 = toTryScanner.rotateEntries(r2)

                    val overlaps = entries1.flatMap { e1 ->
                        entries2.map { e2 ->
                            Overlap(e1, e2, e2 - e1, r2)
                        }
                    }
                    val r = overlaps.groupBy { it.vec }.filterValues { it.size >= 12 }

                    when {
                        r.isNotEmpty() -> {
                            if (r.keys.size > 1) {
                                throw Exception()
                            }
                            val offset = r.keys.first()
                            val inner = (entries1 + entries2.map { it - offset }).toSet().toList()
                            toTryScanner to inner then offset
                        }
                        else -> null
                    }
                }

            if (newRot != null) {
                includedScanners.add(newRot.first)
                sourceScanner = Scanner(-1, (sourceScanner.scans + newRot.second).toSet().toList())
                scannerLocations.add(newRot.third)
            }
        }

        val res = scannerLocations.flatMap { s1 ->
            scannerLocations.map { s2 ->
                s1 manhattenDistance s2
            }

        }.maxOrNull()!!

        return sourceScanner.scans.toSet() to res


    }


}


