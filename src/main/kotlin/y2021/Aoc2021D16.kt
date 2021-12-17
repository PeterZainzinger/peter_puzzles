package y2021

object Aoc2021D16 {

    fun decode(input: String) =
        input.splitChars().map { it.toLong(16).toString(2).padStart(4, '0') }.joinToString("")


    fun versionSum(packetValue: PacketValue): Long {
        return packetValue.version + when (packetValue) {
            is PacketValue.OperatorValue -> packetValue.subPackages.sumOf { versionSum(it) }
            is PacketValue.Literal -> 0L
        }
    }


    fun parsePacket(input: String, maxPackages: Int = Int.MAX_VALUE): Pair<List<PacketValue>, String> {

        if (maxPackages == 0 || input.length < 6 || input.all { it == '0' }) {
            return emptyList<PacketValue>() to input
        }

        val inputArray = input.toCharArray().map { it.toString() }
        val versionData = input.subSequence(0, 3).toString().toLong(2)
        var currentIndex = 6

        return when (val type = input.subSequence(3, 6).toString().toLong(2)) {
            4L -> {
                var number = ""
                while (true) {
                    val cont = inputArray[currentIndex]
                    number += input.subSequence(currentIndex + 1, currentIndex + 5)
                    currentIndex += 5
                    if (cont == "0") {
                        break
                    }
                }
                val restString = input.substring(currentIndex)
                val innerRes = (listOf(
                    PacketValue.Literal(
                        versionData,
                        number.toLong(2)
                    )
                ))
                val childRes = parsePacket(restString, maxPackages - 1)
                (innerRes + childRes.first) to childRes.second
            }
            else -> {
                val lengthId = inputArray[6]

                currentIndex = 7
                when (lengthId) {
                    "0" -> {
                        // next 15 bits are the total length of bits of subpackets
                        val subPacketsLength = input.substring(currentIndex, currentIndex + 15).toLong(2).toInt()
                        currentIndex += 15
                        val inputToParse = input.substring(currentIndex, currentIndex + subPacketsLength)
                        currentIndex += subPacketsLength
                        val (subs, _) = parsePacket(inputToParse, Int.MAX_VALUE)
                        val valueInner = PacketValue.OperatorValue(versionData, type, subs)
                        val remainder = input.substring(currentIndex)

                        val nextRes = parsePacket(remainder, maxPackages - 1)
                        val r = listOf(valueInner) + nextRes.first to nextRes.second
                        r

                    }
                    "1" -> {
                        val numberOfSubPackets = input.substring(currentIndex, currentIndex + 11).toLong(2).toInt()
                        currentIndex += 11
                        val inputToParse = input.substring(currentIndex)
                        val (res, remaining) = parsePacket(inputToParse, numberOfSubPackets)

                        val child = parsePacket(remaining, maxPackages - 1)

                        listOf(PacketValue.OperatorValue(versionData, type, res)) + child.first to child.second

                    }
                    else -> throw Exception("unknown lengthId")
                }


            }

        }

    }

    sealed class PacketValue {
        abstract val version: Long

        abstract fun eval(): Long

        data class Literal(override val version: Long, val value: Long) : PacketValue() {
            override fun eval(): Long {
                return value
            }
        }

        data class OperatorValue(override val version: Long, val operator: Long, val subPackages: List<PacketValue>) :
            PacketValue() {

            override fun eval(): Long {
                return when (operator) {
                    0L -> subPackages.sumOf { it.eval() }
                    1L -> subPackages.fold(1L) { acc, t -> acc * t.eval() }
                    2L -> subPackages.minOf { it.eval() }
                    3L -> subPackages.maxOf { it.eval() }
                    5L -> {
                        when (subPackages.first().eval() > subPackages[1].eval()) {
                            true -> 1L
                            false -> 0L
                        }
                    }
                    6L -> {
                        when (subPackages.first().eval() < subPackages[1].eval()) {
                            true -> 1L
                            false -> 0L
                        }
                    }
                    7L -> {
                        when (subPackages.first().eval() == subPackages[1].eval()) {
                            true -> 1L
                            false -> 0L
                        }
                    }
                    else -> throw Exception("wtf")

                }
            }
        }
    }

}


