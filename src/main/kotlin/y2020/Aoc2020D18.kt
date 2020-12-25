package y2020

object Aoc2020D18 {

    private val parents = Regex("\\([+*0-9 ]+\\)")
    private val plus = Regex("([0-9]+) \\+ ([0-9]+)")

    private fun evalSimpleExp(exp: String): Long {
        val intVal = exp.trim().toLongOrNull()
        if (intVal != null) {
            return intVal
        }
        val opIndex = exp.toCharArray().indexOfLast { it == '+' || it == '*' }
        assert(opIndex > -1)
        val operator = exp[opIndex]
        val a = exp.substring(opIndex + 2).toLong()
        val other = exp.substring(0, opIndex - 1)
        return when (operator) {
            '+' -> evalSimpleExp(other) + a
            '*' -> evalSimpleExp(other) * a
            else -> throw Exception("what op $operator")
        }
    }

    private fun evalSimpleWithPlusFirst(exp: String): Long {
        val intVal = exp.trim().toLongOrNull()
        if (intVal != null) {
            return intVal
        }
        var current = exp
        while (current.contains(plus)) {
            val matched = plus.find(current)!!
            val firstOp = matched.groups[1]!!.value.toInt()
            val secondOp = matched.groups[2]!!.value.toInt()
            current = current.replace(matched.value, (firstOp + secondOp).toString())
        }
        return evalSimpleExp(current)
    }

    fun eval(exp: String, plusFirst: Boolean): Long {
        var current = exp
        val evalSimple = when (plusFirst) {
            true -> ::evalSimpleWithPlusFirst
            false -> ::evalSimpleExp
        }
        while (true) {
            val res = parents.find(current) ?: break
            val matched = current.substring(res.range.start + 1, res.range.endInclusive)
            val matchedfull = current.substring(res.range)
            current = current.replace(
                matchedfull,
                evalSimple(matched).toString()
            )
        }
        return evalSimple(current)
    }
}
