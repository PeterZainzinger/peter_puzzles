package y2020

object Aoc2020D22 {
    sealed class GameResult {
        object Player1Wins : GameResult()
        object Player2Wins : GameResult()
    }

    fun parsePlayer(input: String): List<Int> {
        val lines = input.split("\n")
        return lines.subList(1, lines.size).map { it.toInt() }
    }

    fun part1(s1: List<Int>, s2: List<Int>): Int {
        var statePlayer1 = s1
        var statePlayer2 = s2
        while (statePlayer1.isNotEmpty() && statePlayer2.isNotEmpty()) {

            if (statePlayer1.first() > statePlayer2.first()) {
                statePlayer1 =
                    statePlayer1.subList(1, statePlayer1.size) + listOf(statePlayer1.first(), statePlayer2.first())

                statePlayer2 =
                    statePlayer2.subList(1, statePlayer2.size)
            } else {
                statePlayer2 =
                    statePlayer2.subList(1, statePlayer2.size) + listOf(statePlayer2.first(), statePlayer1.first())

                statePlayer1 =
                    statePlayer1.subList(1, statePlayer1.size)
            }
        }
        return getScore(statePlayer1, statePlayer2)
    }

    private fun getScore(statePlayer1: List<Int>, statePlayer2: List<Int>): Int {
        val winner = when {
            statePlayer1.isEmpty() -> statePlayer2
            else -> statePlayer1
        }
        return winner.reversed().mapIndexed { index, i -> (index + 1) * i }.sum()
    }

    fun part2(s1: List<Int>, s2: List<Int>): Int {
        val (winner, statePlayer1, statePlayer2) = calcWinnerOfGame(s1, s2)
        return getScore(statePlayer1, statePlayer2)
    }

    fun calcWinnerOfGame(
        s1: List<Int>,
        s2: List<Int>,
    ): Triple<GameResult, List<Int>, List<Int>> {
        val memory = mutableSetOf<Pair<List<Int>, List<Int>>>()
        var statePlayer1 = s1
        var statePlayer2 = s2
        while (statePlayer1.isNotEmpty() && statePlayer2.isNotEmpty()) {
            if (memory.contains(statePlayer1 to statePlayer2)) {
                return Triple(GameResult.Player1Wins, statePlayer1, statePlayer2)
            }
            memory.add(statePlayer1 to statePlayer2)
            val winner = calcWinnerOfRound(statePlayer1, statePlayer2)
            if (winner is GameResult.Player1Wins) {
                statePlayer1 =
                    statePlayer1.subList(1, statePlayer1.size) + listOf(statePlayer1.first(), statePlayer2.first())

                statePlayer2 =
                    statePlayer2.subList(1, statePlayer2.size)
            } else {
                statePlayer2 =
                    statePlayer2.subList(1, statePlayer2.size) + listOf(statePlayer2.first(), statePlayer1.first())

                statePlayer1 =
                    statePlayer1.subList(1, statePlayer1.size)
            }
        }
        return Triple(
            when (statePlayer1.isEmpty()) {
                true -> GameResult.Player2Wins
                false -> GameResult.Player1Wins
            },
            statePlayer1,
            statePlayer2
        )
    }

    fun calcWinnerOfRound(
        statePlayer1: List<Int>,
        statePlayer2: List<Int>
    ): GameResult {
        val top1 = statePlayer1.first()
        val top2 = statePlayer2.first()
        return when {
            // winner higher valued card
            top1 > statePlayer1.size - 1 || top2 > statePlayer2.size - 1 -> when {
                top1 > top2 -> GameResult.Player1Wins
                else -> GameResult.Player2Wins
            }
            // new game of recursive
            else -> {
                val (subGameRes, _, _) = calcWinnerOfGame(
                    statePlayer1.subList(1, top1 + 1),
                    statePlayer2.subList(1, top2 + 1),
                )
                subGameRes
            }
        }
    }
}
