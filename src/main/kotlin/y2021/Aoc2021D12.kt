package y2021

import shared.Point
import java.lang.Exception

object Aoc2021D12 {
    data class Node(val name: String, val big: Boolean, var links: MutableList<Node> = mutableListOf()) {

        override fun toString() = name

        companion object {
            fun constructNetwork(lines: List<String>): Pair<Node, Node> {
                val links = lines.map {
                    val (f, t) = it.split("-")
                    f to t
                }
                val nodesMap = links
                    .flatMap { listOf(it.first, it.second) }.toSet()
                    .map { Node(it, it.uppercase() == it) }.associateBy { it.name }
                    .toMutableMap()

                links.forEach { (f, t) ->
                    nodesMap[f]!!.links.add(nodesMap[t]!!)
                    nodesMap[t]!!.links.add(nodesMap[f]!!)
                }

                val start = nodesMap.remove("start")!!
                val end = nodesMap.remove("end")!!

                return start to end
            }
        }
    }

    fun findPath(fixed: List<Node>, target: Node, smallerCavesCondition: Boolean): List<List<Node>> =
        when (val last = fixed.last()) {
            target ->  listOf(fixed)
            else -> {
                val possibleVisits = last.links.filter {
                    val smallerValid = when {
                        it.name == "start" -> !fixed.contains(it)
                        it.name == "end" -> !fixed.contains(it)
                        else -> {
                            val smallerCavesVisited =
                                fixed.filter { !it.big }.groupBy { it.name }.mapValues { it.value.size }.values.maxOrNull()
                                    ?: 0
                            val smallerCaveWasVisited = smallerCavesVisited > 1
                            val count = fixed.filter { i -> i == it }.count()
                            count < when (smallerCaveWasVisited.or(!smallerCavesCondition)) {
                                true -> 1
                                false -> 2
                            }
                        }
                    }
                    it.big.or(smallerValid)
                }
                possibleVisits.flatMap {
                    findPath(fixed.toMutableList().apply { add(it) }, target, smallerCavesCondition)
                }
            }
        }


}


