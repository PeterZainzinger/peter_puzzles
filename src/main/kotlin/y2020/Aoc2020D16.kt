package y2020

object Aoc2020D16 {
    data class TicketClass(val name: String, val options: List<IntRange>) {
        fun containsNumber(value: Int) = options.any { it.contains(value) }
        override fun toString() = name
    }

    data class Ticket(val numbers: List<Int>)

    fun task1(nearby: List<Ticket>, classes: List<TicketClass>) = nearby.map { ticket ->
        ticket.numbers.filterNot { number -> classes.any { option -> option.containsNumber(number) } }.sum()
    }.sum()

    fun validTicket(ticket: Ticket, classes: List<TicketClass>): Boolean = ticket.numbers.all { number ->
        classes.any { option -> option.containsNumber(number) }
    }

    fun matchOptions(options: List<TicketClass>, tickets: List<Ticket>): Map<TicketClass, Int> {
        val positionToOptions = (options.indices).map { index -> index to options }.toMap().toMutableMap()
        tickets.forEach { ticket ->
            ticket.numbers.forEachIndexed { index, i ->
                positionToOptions[index] = positionToOptions[index]!!.filter { it.containsNumber(i) }
            }
        }
        while (!positionToOptions.values.map { it.size }.all { it == 1 }) {
            val before = positionToOptions.toMap()
            before.forEach { (i, value) ->
                if (value.size == 1) {
                    val toRemove = value.first()
                    before.forEach { (j, other) ->
                        if (i != j) {
                            positionToOptions[j] = positionToOptions[j]!!.filter { it.name != toRemove.name }
                        }
                    }
                }
            }
        }
        return positionToOptions.map { (key, value) -> value.first() to key }.toMap()
    }
}
