package y2020

object Aoc2020D08 {
    sealed class Instruction {
        data class NoOp(val value: Int) : Instruction()
        data class Acc(val value: Int) : Instruction()
        data class Jump(val jumpToLine: Int) : Instruction()
    }

    data class ProgramState(
        val acc: Int = 0,
        val currentLine: Int = 0,
        val visitedLines: Set<Int> = emptySet(),
        val result: ProgramResult? = null
    )

    enum class ProgramResult {
        loop, done
    }

    fun execute(program: List<Instruction>, state: ProgramState): ProgramState {
        if (state.currentLine in state.visitedLines) {
            return state.copy(result = ProgramResult.loop)
        }
        val nextState = when (val current = program[state.currentLine]) {
            is Instruction.NoOp -> state.copy(currentLine = state.currentLine + 1)
            is Instruction.Acc -> state.copy(acc = state.acc + current.value, currentLine = state.currentLine + 1)
            is Instruction.Jump -> state.copy(currentLine = state.currentLine + current.jumpToLine)
        }.copy(
            visitedLines = state.visitedLines + setOf(state.currentLine)
        )
        return when {
            nextState.currentLine >= program.size -> nextState.copy(result = ProgramResult.done)
            else -> execute(program, nextState)
        }
    }
}
