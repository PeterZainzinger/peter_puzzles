package y2021

import shared.*


object Aoc2021D21 {

    data class Player(val position: Int, val score: Long = 0) {

        fun increase(value: Int): Player {
            val newPos = (position + value - 1) % 10 + 1
            return copy(
                position = newPos,
                score = score + newPos
            )
        }

        fun toMutable() = PlayerMut(position, score)

    }

    data class PlayerMut(var position: Int, var score: Long = 0) {

        fun increase(value: Int) {
            val newPos = (position + value - 1) % 10 + 1
            position = newPos
            score += newPos
        }

    }

    data class DeterministicDice(
        private var nextVal: Int = 1,
        var rollCount: Long = 0

    ) {
        fun nextValue(): Int {
            val res = nextVal
            nextVal = (nextVal % 100) + 1
            rollCount += 1
            return res
        }
    }

    data class GameState(val p1: Player, val p2: Player, val nextPlayerFlag: Boolean = false) {
        fun newState(diceSum: Int): GameState {
            val np1 = when (nextPlayerFlag) {
                false -> p1.increase(diceSum)
                true -> p1
            }
            val np2 = when (nextPlayerFlag) {
                false -> p2
                true -> p2.increase(diceSum)
            }
            return GameState(np1, np2, !nextPlayerFlag)
        }
    }

    var cache = mutableMapOf<GameState, Pair<Long, Long>>()
    var diceDistribution = mutableMapOf(3 to 1, 4 to 3, 5 to 6, 6 to 7, 7 to 6, 8 to 3, 9 to 1)


    fun countUniverses(state: GameState): Pair<Long, Long> {
        val cachedRes = cache[state]
        if (cachedRes != null) return cachedRes

        var countPlayer1 = 0L
        var countPlayer2 = 0L

        // if not cached, look up
        for (sumOfDice in 3..9) {
            val newState = state.newState(sumOfDice)
            val diceP = diceDistribution[sumOfDice]!!

            // is a winning state
            if (newState.p1.score >= 21 || newState.p2.score >= 21) {
                when (state.nextPlayerFlag) {
                    // player 1
                    false -> {
                        countPlayer1 += diceP
                    }
                    // player 2
                    true -> {
                        countPlayer2 += diceP
                    }
                }
            }
            // not yet winning
            else {
                val (childPlayer1, childPlayer2) = countUniverses(newState)
                countPlayer1 += childPlayer1 * diceP
                countPlayer2 += childPlayer2 * diceP
            }

        }

        val res = countPlayer1 to countPlayer2

        cache[state] = res
        return res

    }


    fun part1(p1: Player, p2: Player): Long {

        val player1 = p1.toMutable()
        val player2 = p2.toMutable()

        var nextPlayer = player1

        fun getNextPlayer() = when (nextPlayer) {
            player1 -> player2
            player2 -> player1
            else -> throw Exception()
        }

        fun notDone() = player1.score < 1000 && player2.score < 1000
        val dice = DeterministicDice()

        while (notDone()) {
            nextPlayer.increase(dice.nextValue() + dice.nextValue() + dice.nextValue())
            nextPlayer = getNextPlayer()
        }

        val allPlayers = listOf(player1, player2).sortedBy { it.score }
        val losingPlayer = allPlayers.first()
        return losingPlayer.score * dice.rollCount

    }

}


