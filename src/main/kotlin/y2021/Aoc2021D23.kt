package y2021

import shared.*
import java.util.*
import kotlin.math.pow

object Aoc2021D23 {

    data class Amphipod(val type: Int)
    data class AmphipodWithCost(val state: AmphipodState, val cost: Int)

    data class AmphipodState(
        val state: Map<Point, Amphipod>,
        val cost: Int = 0,
    ) {
        private val hash by lazy { toString().hashCode() }

        override fun hashCode(): Int = hash
        override fun equals(other: Any?): Boolean = hashCode() == other?.hashCode()
        val isDone: Boolean
            get() = state.entries.all { (p, v) -> isEntryDone(p, v) }


        private fun isValidPath(path: List<Point>) = path.drop(1).all { state[it] == null }
        private fun isEntryDone(p: Point, v: Amphipod) = p.y > 0 && p.x == (v.type + 1) * 2

        fun entriesDone() = state.entries.count { isEntryDone(it.key, it.value) }

        private fun moveMe(from: Point, to: Point): AmphipodState {
            val state = state.toMutableMap()
            state[to] = state.remove(from)!!
            return AmphipodState(state)
        }

        fun generateMoves(currentCost: Int): List<AmphipodWithCost> {
            // not that many transitions are possible, lets do it differently

            // first let find the top in columns
            val topItems = columns.mapNotNull { x ->
                (1..depth).map { y -> state[Point(x, y)]?.to(Point(x, y)) }.firstNotNullOfOrNull { it }
            }
            val toTopItems = topItems.flatMap { (targetV, targetP) ->
                val cost = 10.0.pow(targetV.type).toInt()
                validTopPositions
                    .filter { state[it] == null }
                    .map { innerP -> innerP to path(targetP, innerP) }
                    .filter { isValidPath(it.second) }
                    .map { (innerP, currentPath) ->

                        val constOfMove = cost * (currentPath.size - 1)
                        val newState = moveMe(targetP, innerP)
                        AmphipodWithCost(newState, currentCost + constOfMove)
                    }
            }

            val toBottomItems = validTopPositions.mapNotNull { state[it]?.to(it) }.mapNotNull { (targetV, targetP) ->
                val column = (targetV.type + 1) * 2
                // find last (=lowest) position
                val cost = 10.0.pow(targetV.type).toInt()

                val (_, innerP) = (1..depth).map { y ->
                    val p = Point(column, y)
                    state[p] to (p)
                }.lastOrNull { it.first == null } ?: return@mapNotNull null
                val path = path(targetP, innerP)
                when (isValidPath(path)) {
                    true -> {
                        // all below also have to be same type
                        if ((innerP.y + 1..depth).all { state[Point(innerP.x, it)]?.type == targetV.type }) {
                            val constOfMove = cost * (path.size - 1)
                            val newState = moveMe(targetP, innerP)
                            AmphipodWithCost(newState, currentCost + constOfMove)

                        } else {
                            null
                        }
                    }
                    false -> null
                }
            }
            return toTopItems + toBottomItems
        }

        override fun toString(): String = (0..depth).joinToString("\n") { y ->
            (0..10).joinToString("") { x ->
                when (val e = state[Point(x, y)]) {
                    null -> when ((y == 0).or(x % 2 == 0 && x in 1..9)) {
                        true -> "."
                        false -> "#"
                    }
                    else -> indexToType[e.type]!!
                }
            }
        }

        companion object {
            fun parse(text: String) = AmphipodState(text.splitLines().flatMapIndexed { y, line ->
                line.splitChars().mapIndexed { x, c ->
                    when (val entry = typeToIndex[c]) {
                        null -> null
                        else -> Point(x - 1, y - 1) to Amphipod(entry)
                    }
                }
            }.filterNotNull().toMap())
        }
    }

    data class Entry(
        val state: AmphipodState,
        val cost: Int,
        val progress: Int,
        val generation: Int,
        val history: List<AmphipodState>
    ) {
        val sort by lazy { cost }
    }


    fun search(state: AmphipodState): Int {
        val checkedMoves = mutableSetOf<AmphipodState>()
        val currentQueue = PriorityQueue<Entry>(10000) { a, b ->
            a.sort.compareTo(b.sort)
        }.apply { add(Entry(state, 0, state.entriesDone(), 0, emptyList())) }

        val r = mutableListOf<Pair<Int, List<AmphipodState>>>()
        while (currentQueue.isNotEmpty()) {
            val (nextState, cost, progress, generation, history) = currentQueue.poll()
            if (checkedMoves.contains(nextState)) {
                continue
            }
            checkedMoves.add(nextState)
            val newMoves = nextState.generateMoves(cost)
            val done = newMoves.filter { it.state.isDone }
            if (done.isNotEmpty()) {
                val m = done.minByOrNull { it.cost }!!
                r.add(
                    m.cost to (history + m.state)
                )
                continue
            }
            newMoves
                .filter { !checkedMoves.contains(it.state) }
                .forEach {
                    val newEntry = Entry(it.state, it.cost, it.state.entriesDone(), generation + 1, history + it.state)
                    currentQueue.add(newEntry)
                }
        }
        return r.minByOrNull { it.first }!!.first
    }

    private val indexToType = listOf("a", "b", "c", "d").mapIndexed { index, s -> index to s.toUpperCase() }.toMap()
    private val typeToIndex = indexToType.entries.associate { it.value to it.key }
    private const val depth = 4

    private val columns = listOf(2, 4, 6, 8)
    private val validTopPositions = listOf(0, 1, 3, 5, 7, 9, 10).map { Point(it, 0) }

    private val allValidPoints =
        ((0..10).map { Point(it, 0) } + columns.flatMap { x -> (1..depth).map { y -> Point(x, y) } }).toSet()

    fun IntRange.safe(): Iterable<Int> = when (first <= last) {
        true -> (first..last).toList()
        else -> (last..first).reversed().toList()

    }

    // both inclusive
    fun path(from: Point, to: Point): List<Point> =
        when (from.x == to.x) {
            true -> (from.y..to.y).safe().map { y -> Point(from.x, y) }
            else -> ((from.y downTo 0).map { y -> Point(from.x, y) } + (from.x..to.x).safe().map { x ->
                Point(
                    x,
                    0
                )
            } + (0..to.y).map { y -> Point(to.x, y) })

        }.filterDuplicated()

    private fun List<Point>.filterDuplicated(): List<Point> {
        val res = mutableListOf<Point>()
        forEach {
            if (res.lastOrNull() != it) {
                res.add(it)
            }
        }
        return res
    }
}


