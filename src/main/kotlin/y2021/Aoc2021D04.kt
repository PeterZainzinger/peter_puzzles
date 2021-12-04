package y2021

import java.lang.Exception

object Aoc2021D04 {
    data class Board(val items: List<List<Int>>) {
        fun getColumns(): List<List<Int>> = items.mapIndexed { i, list -> list.mapIndexed { j, _ -> items[j][i] } }
    }

    data class BoardState(val board: Board, val currentDraw: List<Int> = emptyList()) {

        fun addDraw(draw: Int) = copy(currentDraw = currentDraw + listOf(draw))

        private fun isValidRow(items: List<Int>) = (items.toSet().minus(currentDraw)).isEmpty()

        fun score(): Int {
            return board.items.flatten().toSet().minus(currentDraw).sum() * currentDraw.last()
        }

        fun isValid(): Boolean {
            val validRows = board.items.map { isValidRow(it) }.any { it }
            val validColumns =
                board.items.mapIndexed() { index, _ -> isValidRow(board.getColumns()[index]) }.any { it }

            return validRows.or(validColumns)
        }

    }

    fun getFirstWinningBoard(boards: List<Board>, draw: List<Int>): BoardState {
        var boardStates = boards.map { BoardState(it) }
        for (d in draw) {
            boardStates = boardStates.map { it.addDraw(d) }
            val winningBoard = boardStates.filter { it.isValid() }
            if (winningBoard.isNotEmpty()) {
                return winningBoard.first()
            }

        }
        throw Exception()
    }

    fun getLastWinningBoard(boards: List<Board>, draw: List<Int>): BoardState {
        var boardStates = boards.map { BoardState(it) }
        for (d in draw) {
            val beforeBoards = boardStates
            boardStates = boardStates.map { it.addDraw(d) }

            val winningBoard = boardStates.filter { it.isValid() }
            if (winningBoard.size == boards.size) {
                val beforeWinningBoards = beforeBoards.filter { it.isValid() }.map { it.board }
                val newestWinningBoard = boards.toSet().minus(beforeWinningBoards)
                return BoardState(newestWinningBoard.first(), winningBoard.first().currentDraw)

            }
        }
        throw Exception()
    }

}
