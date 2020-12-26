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