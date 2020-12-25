package y2020

import shared.Point

object Aoc2020D12 {

    sealed class MoveInstruction {
        data class Direct(val direction: Point, val value: Int) : MoveInstruction()
        data class Forward(val value: Int) : MoveInstruction()
        data class ChangeDir(val value: Int) : MoveInstruction()
    }

    fun calcEndPosition(
        instructions: List<MoveInstruction>,
        startPosition: Point,
        startDirection: Point,
        f: (Point, Point, MoveInstruction) -> Pair<Point, Point>
    ) = instructions.fold(startPosition to startDirection) { acc, move -> f(acc.first, acc.second, move) }.first

    private fun rotateLeft(value: Int, direction: Point): Point = when (value) {
        0 -> direction
        else -> rotateLeft(value - 90, Point(-direction.y, direction.x))
    }

    fun calcNextPosition(currentPosition: Point, currentDir: Point, instruction: MoveInstruction) =
        when (instruction) {
            is MoveInstruction.Direct -> currentPosition + instruction.direction * instruction.value to currentDir
            is MoveInstruction.Forward -> currentPosition + currentDir * instruction.value to currentDir
            is MoveInstruction.ChangeDir -> currentPosition to rotateLeft(instruction.value, currentDir)
        }

    fun calcNextPosition2(currentPosition: Point, currentDir: Point, instruction: MoveInstruction) =
        when (instruction) {
            is MoveInstruction.Direct -> currentPosition to (currentDir + instruction.direction * instruction.value)
            is MoveInstruction.Forward -> (currentPosition + currentDir * instruction.value) to currentDir
            is MoveInstruction.ChangeDir -> currentPosition to rotateLeft(instruction.value, currentDir)
        }
}
