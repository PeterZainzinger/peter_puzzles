package y2020

object Aoc2020D23 {
    fun runRound(input: List<Int>, loops: Int): CircularList {
        val list = CircularList(input)
        var i = 0
        var currentCup = list.start
        val min = input.minOrNull()!!
        val max = input.maxOrNull()!!
        while (i < loops) {
            val removed = list.removeNNodesAfter(currentCup, 3)
            val removedValues = removed.valuesAfter()
            var destinationCup = list.valueMap[wrapAround(currentCup.value - 1, min, max)]!!
            while (removedValues.contains(destinationCup.value)) {
                val tryIndex = wrapAround(destinationCup.value - 1, min, max)
                destinationCup = list.valueMap[tryIndex]!!
            }
            list.insertNodesAfter(destinationCup, removed)
            currentCup = currentCup.next!!
            i++
        }

        return list
    }

    private fun after1List(list: CircularList): String {
        var target1: CircularList.Node? = list.start
        while (target1!!.value != 1) {
            target1 = target1.next
        }
        var next = target1.next!!
        val res = mutableListOf<Int>()
        while (next != target1) {
            res.add(next.value)
            next = next.next!!
        }
        return res.joinToString("")
    }

    private fun find2AfterProduct(list: CircularList): Int {
        var target1: CircularList.Node? = list.start
        while (target1!!.value != 1) {
            target1 = target1.next
        }
        val f1 = target1.next!!
        val f2 = f1.next!!
        return f1.value * f2.value
    }

    class CircularList(initialValues: List<Int>) {

        data class Node(val value: Int, var next: Node?) {
            override fun toString(): String = "Node $value"

            fun valuesAfter(): MutableList<Int> {
                var res = mutableListOf<Int>()
                var current: Node? = this
                while (current != null) {
                    res.add(current.value)
                    current = current.next
                }
                return res
            }
        }

        var start: Node
        val valueMap: MutableMap<Int, Node> = mutableMapOf()

        fun removeNNodesAfter(node: Node, many: Int): Node {
            var target = node.next!!
            var count = 0
            var before: Node? = null
            while (count < many) {
                before = target
                target = target.next!!
                count++
            }
            before!!.next = null
            val res = node.next!!
            // bridge start-> end
            node.next = target
            return res
        }

        fun insertNodesAfter(target: Node, toInsert: Node) {
            val end = target.next!!
            var prev = target
            var next: Node? = toInsert
            while (next != null) {
                prev.next = next
                prev = next
                next = next.next
            }
            prev.next = end
        }

        override fun toString(): String {
            val allNodes = mutableListOf(start)
            var current = start.next
            while (current != null && current != start) {
                allNodes.add(current)
                current = current.next
            }
            return "List (${allNodes.map { it.value }.joinToString(" ")})"
        }

        init {
            start = Node(initialValues.first(), null)
            valueMap[initialValues.first()] = start
            var before = start
            initialValues.subList(1, initialValues.size).forEach {
                val next = Node(it, null)
                valueMap[it] = next
                before.next = next
                before = next
            }
            before.next = start
        }
    }

    private fun wrapAround(value: Int, min: Int, max: Int): Int = when {
        value < min -> max
        value > max -> min
        else -> value
    }
}
