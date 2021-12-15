package y2021

import shared.Point
import shared.splitLines
import kotlin.math.abs
import kotlin.math.absoluteValue

object Aoc2021D15 {

    fun constructBiggerMap(map: Map<Point, Int>, many: Int = 5): Map<Point, Int> {
        val endX = map.keys.maxByOrNull { it.x }!!.x + 1
        val endY = map.keys.maxByOrNull { it.y }!!.y + 1

        val offsets = (0 until many).flatMap { offsetX ->
            (0 until many).map { offsetY ->
                Point(offsetX * endX, offsetY * endY) to (offsetX + offsetY)
            }
        }.toMap()

        val biggerMap = map.entries.flatMap { (originalP, originalV) ->
            offsets.entries.map { (innerP, innerV) ->
                (originalP + innerP) to ((originalV + innerV - 1) % 9) + 1
            }
        }.toMap()

        return biggerMap.filterKeys { it.distanceToDiagonal() < 60 }

    }

    fun Point.distanceToDiagonal(): Int = abs(x-y)

    fun pathWeight(map: Map<Point, Int>): Int {
        val start = Point(0, 0)
        val endX = map.keys.maxByOrNull { it.x }!!.x
        val endY = map.keys.maxByOrNull { it.y }!!.y
        val end = Point(endX, endY)

        val graph = constructGraph(map)
        val pathTree = dijkstra(graph, start)
        val shortestPath = shortestPath(pathTree, start, end)

        val sum = shortestPath.drop(1).sumOf { map[it]!! }
        println(sum)
        return sum
    }

    private val neighborOffsetsNotDiagonal =
        (-1..1).flatMap { x ->
            (-1..1).map { y ->
                Point(x, y)
            }
        }
            .filter { (it.x != 0).or(it.y != 0) }
            .filter { !(it.x.absoluteValue == 1 && it.y.absoluteValue == 1) }


    private fun constructGraph(map: Map<Point, Int>): Graph<Point> {
        val vertices = map.keys
        val edges = map.mapValues { outer ->
            neighborOffsetsNotDiagonal.map { outer.key + it }.filter { map.keys.contains(it) }.toSet()
        }
        val points = edges.entries.flatMap { (from, too) -> too.map { (from to it) to map[it]!! } }.toMap()
        return Graph(vertices, edges, points)
    }

    // have implemented djikstra to often myself

    data class Graph<T>(
        val vertices: Set<T>,
        val edges: Map<T, Set<T>>,
        val weights: Map<Pair<T, T>, Int>
    )

    fun <T> dijkstra(graph: Graph<T>, start: T): Map<T, T?> {
        val S: MutableSet<T> = mutableSetOf() // a subset of vertices, for which we know the true distance

        val delta = graph.vertices.map { it to Int.MAX_VALUE }.toMap().toMutableMap()
        delta[start] = 0

        val previous: MutableMap<T, T?> = graph.vertices.map { it to null }.toMap().toMutableMap()

        while (S != graph.vertices) {
            val v: T = delta
                .filter { !S.contains(it.key) }
                .minByOrNull { it.value }!!
                .key

            graph.edges.getValue(v).minus(S).forEach { neighbor ->
                val newPath = delta.getValue(v) + graph.weights.getValue(Pair(v, neighbor))

                if (newPath < delta.getValue(neighbor)) {
                    delta[neighbor] = newPath
                    previous[neighbor] = v
                }
            }

            S.add(v)
        }

        return previous.toMap()
    }

    fun <T> shortestPath(shortestPathTree: Map<T, T?>, start: T, end: T): List<T> {
        fun pathTo(start: T, end: T): List<T> {
            if (shortestPathTree[end] == null) return listOf(end)
            return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
        }

        return pathTo(start, end)
    }



}


