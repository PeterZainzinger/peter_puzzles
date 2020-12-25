package y2020

object Aoc2020D17 {
    data class Index(val x: Int, val y: Int, val z: Int, val a: Int) {
        operator fun plus(offset: Index) = Index(offset.x + x, y + offset.y, z + offset.z, a + offset.a)
    }

    private val allOffsets =
        (-1..1).flatMap { x ->
            (-1..1).flatMap { y ->
                (-1..1).flatMap { z ->
                    (-1..1).map { a ->
                        Index(x, y, z, a)
                    }
                }
            }
        }
    private val neighborOffset = allOffsets.filterNot { (x, y, z, a) -> x == 0 && y == 0 && z == 0 && a == 0 }

    private fun getAllNeighbors(index: Index): Set<Index> = allOffsets.map { offset -> offset + index }.toSet()
    private fun getActiveNeighbors(index: Index, state: Set<Index>) =
        neighborOffset.map { offset -> offset + index }.filter { state.contains(it) }

    fun newState(state: Set<Index>) = state.flatMap { getAllNeighbors(it) }.filter { index ->
        val activeNeigbors = getActiveNeighbors(index, state)
        when (state.contains(index)) {
            true -> (2..3).contains(activeNeigbors.size)
            false -> activeNeigbors.size == 3
        }
    }.toSet()
}
