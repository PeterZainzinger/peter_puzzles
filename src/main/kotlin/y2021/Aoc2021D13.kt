package y2021

import shared.Point
import shared.splitLines
import java.lang.Exception

typealias Grid = Set<Point>

object Aoc2021D13 {
    enum class Axis {
        x, y
    }

    data class Instruction(val axis: Axis, val value: Int)
    data class Input(val grid: Grid, val instructions: List<Instruction>) {

        companion object {
            fun parse(text: String): Input {
                val (pointsText, instructionText) = text.split("\n\n")
                val points = pointsText.splitLines().map { it.trim() }.map {
                    val (x, y) = it.split(",")
                    Point(x.toInt(), y.toInt())
                }
                val instructions = instructionText.splitLines().map { it.trim() }
                    .map { it.split(" ")[2]!! }
                    .map {
                        val (a, v) = it.split("=")
                        Instruction(
                            when (a) {
                                "x" -> Axis.x
                                "y" -> Axis.y
                                else -> throw Exception()
                            }, v.toInt()
                        )
                    }

                val grid = points.toSet()
                return Input(grid, instructions)
            }
        }
    }

    fun foldAll(input: Input) = input.instructions.fold(input.grid) { g, i -> foldInstruction(g, i) }

    fun foldInstruction(grid: Grid, instruction: Instruction) = when (instruction.axis) {
        Axis.x -> foldX(grid, instruction.value)
        Axis.y -> foldY(grid, instruction.value)
    }

    fun foldX(grid: Grid, value: Int): Grid {
        val left = grid.filter { it.x <= value }
        val right = grid.filter { it.x > value }
        return (left + right.map {
            it.copy(x = 2 * value - it.x)
        }).toSet()
    }

    fun foldY(grid: Grid, value: Int): Grid {
        val below = grid.filter { it.y <= value }
        val above = grid.filter { it.y > value }
        return (below + above.map {
            it.copy(y = 2 * value - it.y)
        }).toSet()
    }

    fun printGrid(grid: Set<Point>): String {
        val xDimens = grid.maxByOrNull { it.x }!!.x
        val yDimens = grid.maxByOrNull { it.y }!!.y
        val p = (0..yDimens).map { y ->
            (0..xDimens).map { x ->
                when (grid.contains(Point(x, y))) {
                    true -> "#"
                    false -> "."
                }
            }.joinToString("")
        }.joinToString("\n")
        println(p)
        return p
    }


}


