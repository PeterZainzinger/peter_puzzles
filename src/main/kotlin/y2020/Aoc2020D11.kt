package y2020

typealias SeatWorld = List<List<Aoc2020D11.SeatState>>

object Aoc2020D11 {
    sealed class SeatState(val char: Char) {
        object Empty : SeatState('L')
        object Occupied : SeatState('#')
        object Floor : SeatState('.')
    }

    fun countSeated(seatWorld: SeatWorld) =
        seatWorld.map { it.filter { it is SeatState.Occupied }.count() }.sum()

    fun untilStable(input: SeatWorld): SeatWorld {
        var before: SeatWorld? = null
        var current = input
        while (before != current) {
            val beforeTmp = current
            current = calcNextState(current)
            before = beforeTmp
        }
        return current
    }

    fun untilStable2(input: SeatWorld): SeatWorld {
        var before: SeatWorld? = null
        var current = input
        while (before != current) {
            val beforeTmp = current
            current = calcNextState2(current)
            before = beforeTmp
        }
        return current
    }

    private fun getSafe(seatWorld: SeatWorld, x: Int, y: Int): SeatState? = seatWorld.getOrNull(y)?.getOrNull(x)

    private fun getAdjent(seatWorld: SeatWorld, i: Int, j: Int) = listOf(
        -1 to -1,
        -1 to 0,
        -1 to 1,
        0 to -1,
        0 to 1,
        1 to -1,
        1 to 0,
        1 to 1,
    )

        .map { (dx, dy) -> (i + dx) to (j + dy) }
        .mapNotNull { (i, j) -> getSafe(seatWorld, i, j) }

    private fun getAdjent2(seatWorld: SeatWorld, i: Int, j: Int) = listOf(
        -1 to -1,
        -1 to 0,
        -1 to 1,
        0 to -1,
        0 to 1,
        1 to -1,
        1 to 0,
        1 to 1,
    ).mapNotNull { (dx, dy) ->
        var offset = 1
        var res: SeatState? = null
        while (true) {
            val target = getSafe(seatWorld, i + offset * dx, j + offset * dy)
            if (target == null) {
                res = null
                break
            } else if (target != SeatState.Floor) {
                res = target
                break
            }
            offset++
        }
        res
    }

    fun calcNextState(seatWorld: SeatWorld) = seatWorld.mapIndexed { y, row ->
        row.mapIndexed { x, item ->
            val adjent = getAdjent(seatWorld, x, y)
            val adjentSeated = adjent.filter { it is SeatState.Occupied }
            when (item) {
                is SeatState.Floor -> SeatState.Floor
                is SeatState.Empty -> when {
                    adjentSeated.isEmpty() -> SeatState.Occupied
                    else -> SeatState.Empty
                }
                is SeatState.Occupied -> when {
                    adjentSeated.size > 3 -> SeatState.Empty
                    else -> SeatState.Occupied
                }
            }
        }
    }

    fun calcNextState2(seatWorld: SeatWorld) = seatWorld.mapIndexed { y, row ->
        row.mapIndexed { x, item ->
            val adjent = getAdjent2(seatWorld, x, y)
            val adjentSeated = adjent.filter { it is SeatState.Occupied }
            when (item) {
                is SeatState.Floor -> SeatState.Floor
                is SeatState.Empty -> when {
                    adjentSeated.isEmpty() -> SeatState.Occupied
                    else -> SeatState.Empty
                }
                is SeatState.Occupied -> when {
                    adjentSeated.size > 4 -> SeatState.Empty
                    else -> SeatState.Occupied
                }
            }
        }
    }
}
