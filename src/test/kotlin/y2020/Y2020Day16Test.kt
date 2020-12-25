package y2020

import BaseTest
import assertNumber
import org.junit.jupiter.api.Test
import shared.splitEmptyLines
import shared.splitLines
import y2020.Aoc2020D16.matchOptions
import y2020.Aoc2020D16.task1
import y2020.Aoc2020D16.validTicket

data class AOC2020D16(
    val ticketClasses: List<Aoc2020D16.TicketClass>,
    val ownTicket: Aoc2020D16.Ticket,
    val otherTickets: List<Aoc2020D16.Ticket>,
)

private fun parseTicket(input: String) = input.split("\n").mapIndexed { index, s ->
    when {
        index == 0 || s == "" -> null
        else -> Aoc2020D16.Ticket(s.split(",").map { it.toInt() })
    }
}.filterNotNull()

class Y2020Day16Test : BaseTest<AOC2020D16>(2020, 16) {

    override fun parseInput(input: String): AOC2020D16 {
        val (ticketClassText, ownTicketText, otherTicketsText) = input.splitEmptyLines()
        val ticketClasses = ticketClassText.splitLines().map { it.split(":") }
            .map {
                Aoc2020D16.TicketClass(
                    it.first(),
                    it[1].split(" or ").map {
                        val range = it.split("-").map { it.trim().toInt() }
                        range[0]..range[1]
                    }
                )
            }
        val myTicket = parseTicket(ownTicketText).first()
        val nearbyTickets = parseTicket(otherTicketsText)
        return AOC2020D16(ticketClasses, myTicket, nearbyTickets)
    }

    @Test
    fun part1() {
        val (ticketOptions, myTicket, nearbyTickets) = getInput()
        val validTickets = nearbyTickets.filter { validTicket(it, ticketOptions) }
        val matched = matchOptions(ticketOptions, validTickets)
        val res = task1(nearbyTickets, ticketOptions)
        assertNumber(21956, res)
    }

    @Test
    fun part2() {
        val (ticketOptions, myTicket, nearbyTickets) = getInput()
        val validTickets = nearbyTickets.filter { validTicket(it, ticketOptions) }
        val matched = matchOptions(ticketOptions, validTickets)
        val res = matched.filter { it.key.name.startsWith("departure") }
            .map { myTicket.numbers[it.value] }.map { it.toLong() }.fold(1L, { a, b -> a * b })
        assertNumber(3709435214239L, res)
    }
}
