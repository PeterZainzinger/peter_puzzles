package y2020

import kotlin.math.floor
import kotlin.math.sqrt

object Aoc2020D20 {

    data class GridPosition(val x: Int, val y: Int) {
        operator fun plus(position: GridPosition) = GridPosition(x + position.x, y + position.y)
    }

    data class SearchState(
        val filled: Map<GridPosition, Tile>,
        val remainingOptions: List<Tile>,
        val width: Int,
    )

    sealed class Pixel(val item: Char) {
        object Filled : Pixel('#')
        object Empty : Pixel('.')
    }

    data class Tile(val id: Int, val pixels: List<List<Pixel>>) {
        override fun toString() =
            "\nTile $id:\n" + pixels.joinToString("\n") { it.joinToString(separator = "") { it.item.toString() } } + "\n\n"

        fun alignsToRight(right: Tile) = getRightEdge() == right.getLeftEdge()
        fun alignsToBottom(bottom: Tile) = getBottomEdge() == bottom.getTopEdge()

        fun getPixelSafe(position: GridPosition) = when {
            position.x < pixels.size && position.y < pixels.size -> pixels[position.y][position.x]
            else -> null
        }

        val allOptions by lazy {
            val rot0 = this
            val rot1 = rotate()
            val rot2 = rot1.rotate()
            val rot3 = rot2.rotate()
            listOf(rot0, rot1, rot2, rot3).flatMap { source ->
                listOf(source, source.flipX(), source.flipY(), source.flipX().flipY())
            }.toSet().toList()
        }

        fun roughness() = pixels.map {
            it.map {
                when (it) {
                    is Pixel.Filled -> 1
                    is Pixel.Empty -> 0
                }
            }.sum()
        }.sum()

        private fun flipY() = Tile(id, pixels.reversed())
        private fun flipX() = Tile(id, pixels.map { it.reversed() })
        private fun rotate(): Tile {
            val pixelsRotated = pixels.map { it.toMutableList() }.toMutableList()
            for (i in pixels.indices) {
                val row = pixels[i]
                for (j in pixels.indices) {
                    val source = row[j]
                    pixelsRotated[j][i] = source
                }
            }
            return Tile(id, pixelsRotated)
        }

        private fun getTopEdge() = pixels.first()
        private fun getBottomEdge() = pixels.last()
        private fun getLeftEdge() = pixels.map { it.first() }
        private fun getRightEdge() = pixels.map { it.last() }

        fun contentLines() = pixels.subList(1, pixels.size - 1).map { it.subList(1, it.size - 1) }.map {
            it.map { it.item }.joinToString("")
        }
    }

    fun parseTile(input: List<String>) = input.map {
        it.map {
            when (it) {
                '#' -> Aoc2020D20.Pixel.Filled
                '.' -> Aoc2020D20.Pixel.Empty
                else -> throw Exception("invalid pixel $it")
            }
        }
    }

    fun searchForNext(state: SearchState): List<SearchState> {
        val nextIndex = state.filled.size
        val x = nextIndex % state.width
        val y = floor(nextIndex / state.width.toDouble()).toInt()
        val allOptions = state.remainingOptions.flatMap { it.allOptions }
        val filtered = allOptions
            // if top matches
            .filter { opt -> y == 0 || state.filled[GridPosition(x, y - 1)]!!.alignsToBottom(opt) }
            // if left matches
            .filter { opt -> x == 0 || state.filled[GridPosition(x - 1, y)]!!.alignsToRight(opt) }

        return filtered.map { taken ->
            state.copy(
                remainingOptions = state.remainingOptions.filterNot { it.id == taken.id },
                filled = state.filled.toMutableMap().apply { put(GridPosition(x, y), taken) }
            )
        }
    }

    fun searchArrangment(tiles: List<Tile>): SearchState {
        val width = sqrt(tiles.size.toDouble()).toInt()
        val startState = Aoc2020D20.SearchState(
            filled = emptyMap(),
            remainingOptions = tiles,
            width = width
        )
        var currentStates = listOf(startState)
        tiles.indices.forEach { loop ->
            currentStates = currentStates.flatMap { searchForNext(it) }
        }
        return currentStates.first()
    }

    fun buildWholeAndSearch(tiles: List<Tile>, monsterPattern: String): Int {
        val width = sqrt(tiles.size.toDouble()).toInt()
        val oneOption = searchArrangment(tiles)
        var picture = ""
        for (lineIndex in 0 until width) {
            val linesPerColumn =
                (0 until width).map { oneOption.filled[Aoc2020D20.GridPosition(it, lineIndex)]!!.contentLines() }
            val innerLength = linesPerColumn.first().size
            for (innerIndex in 0 until innerLength) {
                picture += linesPerColumn.joinToString("") { it[innerIndex] }
                picture += "\n"
            }
        }
        picture = picture.trim()
        val pictureTile = Aoc2020D20.Tile(0, parseTile(picture.trim().split("\n")))
        val monsterIndices = monsterPattern.split("\n").mapIndexed { y, s ->
            s.toCharArray().mapIndexed { x, c ->
                when (c) {
                    '#' -> Aoc2020D20.GridPosition(x, y)
                    else -> null
                }
            }.filterNotNull()
        }.flatten()

        return pictureTile.allOptions.map { picture ->
            var count = 0
            for (i in picture.pixels.indices) {
                for (j in picture.pixels.indices) {
                    val startPosition = Aoc2020D20.GridPosition(i, j)
                    val matched = monsterIndices.map { startPosition + it }.map { picture.getPixelSafe(it) }
                        .all { it == Aoc2020D20.Pixel.Filled }
                    if (matched) {
                        count++
                    }
                }
            }
            when (count > 0) {
                true -> picture.roughness() - count * monsterIndices.size
                false -> null
            }
        }.filterNotNull().first()
    }
}
