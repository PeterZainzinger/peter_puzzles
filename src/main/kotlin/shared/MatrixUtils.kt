package shared

import smile.math.matrix.Matrix


fun List<List<Double>>.toMatrix() :Matrix{
    val rowsLengths = map { it.size }.toSet()
    if (rowsLengths.size > 1) {
        throw Exception("uneven length")
    }
    val res = Matrix(rowsLengths.first(), size)
    forEachIndexed { rowIndex, row ->
        row.mapIndexed { columnIndex, value ->
            res.set(rowIndex, columnIndex, value)
        }
    }
    return res
}


data class Matrix3(val entries: List<List<Int>>) {

    companion object {

        val I = Matrix3((0..2).map { x ->
            (0..2).map { y ->
                when (x) {
                    y -> 1
                    else -> 0
                }
            }
        })
    }


    fun transpose() = Matrix3(entries.mapIndexed { y, c ->
        c.mapIndexed { x, _ ->
            entries[x][y]
        }
    })

    operator fun times(other: Matrix3): Matrix3 {
        val res = entries.map { it.map { 0 }.toMutableList() }.toMutableList()
        (0 until 3).forEach { i ->
            (0 until 3).forEach { j ->
                (0 until 3).forEach { k ->
                    res[i][j] += entries[i][k] * other.entries[k][j]
                }
            }

        }
        return Matrix3(res)
    }

    operator fun times(other: Point3): Point3 {
        val res = (0..3).map { 0 }.toMutableList()

        (0 until 3).map { row ->
            (0 until 3).forEach { c ->
                res[row] += entries[row][c] * other.entries[c]
            }
        }
        val (x, y, z) = res
        return Point3(x, y, z)
    }


    val det by lazy {
        val a = entries[0][0]
        val b = entries[0][1]
        val c = entries[0][2]
        val d = entries[1][0]
        val e = entries[1][1]
        val f = entries[1][2]
        val g = entries[2][0]
        val h = entries[2][1]
        val i = entries[2][2]
        a * e * i + b * f * g + c * d * h - g * e * c - h * f * a - i * d * b
    }


}



