package y2021

object Aoc2021D01 {

    fun howManyIncreasing(input: List<Int>): Int {
        var count = 0
        for (i in 1 until input.size) {
            if (input[i - 1] < input[i]) {
                count++
            }

        }
        return count
    }

    fun howManyIncreasingSliding(input: List<Int>): Int {

        fun getSlice(index: Int): Int {
            return input[index - 2] + input[index - 1] + input[index]
        }

        var count = 0
        for (i in 3 until input.size) {
            if (getSlice(i - 1) < getSlice(i)) {
                count++
            }

        }
        return count
    }

}
