package y2021

import shared.Point
import shared.splitLines
import java.lang.Exception
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.ceil


object Aoc2021D18 {


    enum class Direction {
        Left, Right
    }

    @JvmInline
    value class NodePath(private val p: List<Direction>) {

        val depth: Int
            get() = p.size

        val isEmpty: Boolean
            get() = depth == 0

        val first: Direction
            get() = p.first()

        fun allButFirst() = NodePath(p.drop(1))


        fun append(d: Direction) = NodePath(p + listOf(d))

    }

    data class NodeWitPath<T : SnailFishNumber>(val node: T, val path: NodePath)

    sealed class SnailFishNumber {
        data class Raw(val value: Int) : SnailFishNumber() {
            override fun toString(): String = "$value"
        }

        data class Node(val left: SnailFishNumber, val right: SnailFishNumber) : SnailFishNumber() {
            override fun toString(): String = "[$left,$right]"
        }

        fun magnitude(): Long = when (this) {
            is Raw -> this.value.toLong()
            is Node -> 3 * left.magnitude() + 2 * right.magnitude()
        }

        operator fun plus(other: SnailFishNumber) = Node(this, other)

        fun reduce(): SnailFishNumber {
            var current = this
            while (current.explode() != null || current.splitNumber() != null) {
                while (current.explode() != null) {
                    current = current.explode()!!
                }
                if (current.splitNumber() != null) {
                    current = current.splitNumber()!!
                }
            }
            return current

        }

        // return null if there is no way to explode
        fun explode(): SnailFishNumber? = when (val toExplode = findFirstToExplode()) {
            null -> null
            else -> {
                var newValue = this
                val leftNode = toExplode.node.left as Raw
                val rightNode = toExplode.node.right as Raw
                val (leftNeighbor, rightNeighbor) = findFirstLeftRight(toExplode)
                if (leftNeighbor != null) {
                    newValue = newValue.addToNode(leftNeighbor.path, leftNode.value)
                }
                if (rightNeighbor != null) {
                    newValue = newValue.addToNode(rightNeighbor.path, rightNode.value)
                }
                newValue = newValue.updateNode(toExplode.path) { Raw(0) }
                newValue
            }
        }

        fun splitNumber(): SnailFishNumber? = when (val toSplit = findFirstGreater9()) {
            null -> null
            else -> {
                this.updateNode(toSplit.path) {
                    Node(Raw(toSplit.node.value / 2), Raw(ceil(toSplit.node.value / 2.0).toInt()))
                }
            }
        }

        private fun findFirstToExplode(): NodeWitPath<Node>? = traverseNumber()
            .filter { it.node is Node }
            .filterIsInstance<NodeWitPath<Node>>()
            .firstOrNull { (number, path) ->
                path.depth == 4 && number.left is Raw && number.right is Raw
            }

        private fun findFirstGreater9() = traverseNumber()
            .filter { it.node is Raw }
            .filterIsInstance<NodeWitPath<Raw>>()
            .firstOrNull() { (number, _) ->
                number.value > 9
            }

        private fun addToNode(path: NodePath, v: Int) = updateNode(path) {
            it as Raw
            it.copy(value = it.value + v)
        }

        private fun updateNode(path: NodePath, f: (SnailFishNumber) -> SnailFishNumber): SnailFishNumber =
            when (path.isEmpty) {
                true -> f(this)
                false -> when (this) {
                    is Raw -> this
                    is Node -> {
                        when (path.first) {
                            Direction.Left -> Node(left.updateNode(path.allButFirst(), f), right)
                            Direction.Right -> Node(left, right.updateNode(path.allButFirst(), f))
                        }
                    }
                }
            }


        private fun findFirstLeftRight(item: NodeWitPath<*>): Pair<NodeWitPath<Raw>?, NodeWitPath<Raw>?> {
            val traversal = traverseNumber()
            val itemIndex = traversal.indexOf(item)
            val leftSideOfTraversal = traversal.subList(0, itemIndex - 1)
            val rightSideOfTraversal = traversal.subList(itemIndex + 2, traversal.size)

            val leftNeighbor = leftSideOfTraversal
                .filter { it.node is Raw }
                .filterIsInstance<NodeWitPath<Raw>>()
                .lastOrNull()

            val rightNeighbor = rightSideOfTraversal
                .filter { it.node is Raw }
                .filterIsInstance<NodeWitPath<Raw>>()
                .firstOrNull()

            return leftNeighbor to rightNeighbor
        }

        private fun traverseNumber(currentPath: NodePath = NodePath(emptyList())): List<NodeWitPath<out SnailFishNumber>> =
            when (this) {
                is Raw -> listOf(NodeWitPath(this, currentPath))
                is Node ->
                    left.traverseNumber(currentPath.append(Direction.Left)) +
                            listOf(NodeWitPath(this, currentPath)) +
                            right.traverseNumber(currentPath.append(Direction.Right))

            }


        companion object {
            fun parse(line: String): SnailFishNumber = when (line.first()) {
                '[' -> {
                    val last = line.last()
                    if (last != ']') {
                        throw Exception()
                    }
                    val (leftPart, rightPart) = splitChar(line.substring(1 until line.length - 1))
                    Node(parse(leftPart), parse(rightPart))
                }
                else -> {
                    val firstDigit = line.first().toString().toInt()
                    val leftNode = Raw(firstDigit)
                    when (line.length > 1) {
                        true -> {
                            if (line[1] != ',') {
                                throw Exception()
                            }
                            Node(leftNode, parse(line.substring(2 until line.length)))
                        }
                        else -> {
                            leftNode
                        }

                    }
                }
            }

            // find comma with level zero
            private fun splitChar(input: String): Pair<String, String> {
                val stack = mutableListOf<Int>()
                var currentPos = 0

                while (input[currentPos] != ',' || stack.isNotEmpty()) {
                    when (input[currentPos]) {
                        '[' -> stack.add(0)
                        ']' -> stack.removeLast()
                    }
                    currentPos++
                }
                return input.substring(0 until currentPos) to input.substring(currentPos + 1 until input.length)
            }
        }

    }


}


