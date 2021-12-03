package y2021

import shared.Point
import shared.Point3
import java.util.*

object Aoc2021D02 {

    fun parse(line: String): Command {
        val (c, i) = line.split(" ")
        val diff = i.toInt()
        return when (c) {
            "up" -> Command.Up(diff)
            "down" -> Command.Down(diff)
            "forward" -> Command.Forward(diff)
            else -> throw Exception("unhandled command")
        }

    }

    sealed class Command(val diff: Int, val direction: Point) {
        fun vector() = direction * diff

        class Up(diff: Int) : Command(diff, Point(0, -1))
        class Down(diff: Int) : Command(diff, Point(0, 1))
        class Forward(diff: Int) : Command(diff, Point(1, 0))
    }

    fun executeCommands(start: Point, commands: List<Command>): Point {
        var current = start
        commands.forEach {
            current += it.vector()
        }
        return current
    }

    fun executeCommands2(start: Point3, commands: List<Command>): Point3 {
        var current = start
        commands.forEach {
            current += when (it) {
                is Command.Down -> Point3(0, 0, it.diff)
                is Command.Up -> Point3(0, 0, -it.diff)
                is Command.Forward -> Point3(it.diff, it.diff * current.z, 0)
            }
        }
        return current
    }

}
