package y2021

object Aoc2021D06 {
    fun countAfterDay(input: List<Int>, gen: Int): Long {
        var currentFish = input.groupBy { it }.mapValues { it.value.size.toLong() }
        repeat(gen) {
            val currentFishAged = currentFish.mapKeys { it.key - 1 }.toMutableMap()
            val toOld = currentFishAged.remove(-1) ?: 0
            currentFishAged.increase(6, toOld)
            currentFishAged.increase(8, toOld)
            currentFish = currentFishAged
        }
        return currentFish.values.toList().sum()
    }

}

fun <K> MutableMap<K, Long>.increase(key: K, n: Long) {
    val entry = get(key) ?: 0
    set(key, entry + n)
}