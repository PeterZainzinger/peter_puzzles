package y2020

object Aoc2020D09 {
    fun findNonMatching(input: List<Long>, currentIndex: Int, windowSize: Int): Set<Long> {
        if (currentIndex >= input.size - 1) {
            return emptySet()
        }
        val target = input[currentIndex]
        return when (canBePresentedAsSum(target, input.subList(currentIndex - windowSize, currentIndex))) {
            false -> setOf(target)
            true -> emptySet()
        } + findNonMatching(input, currentIndex + 1, windowSize)
    }

    fun canBePresentedAsSum(target: Long, options: List<Long>): Boolean {
        for (i in options) {
            for (j in options) {
                if (i != j && i + j == target) {
                    return true
                }
            }
        }
        return false
    }
    fun findContinousSum(target: Long, options: List<Long>): List<Long>? {
        for (pivot in 0..options.size) {
            for (i in 0..options.size - pivot) {
                val subList = options.subList(pivot, pivot + i)
                val subSum = subList.sum()
                if (subSum == target) {
                    return subList
                }
                if (subSum > target) {
                    break
                }
            }
        }
        return null
    }

}


