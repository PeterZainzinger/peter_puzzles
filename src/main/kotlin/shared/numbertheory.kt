package shared

import kotlin.math.sqrt

fun gcd(a: Long, b: Long): Long = when (b) {
    0L -> a
    else -> gcd(b, a % b)
}

fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

fun gcdExt(p: Long, q: Long): Triple<Long, Long, Long> {
    if (q == 0L) return Triple(p, 1, 0)
    val (c1, c2, c3) = gcdExt(q, p % q)
    return Triple(c1, c3, c2 - p / q * c3)
}

// https://de.wikipedia.org/wiki/Chinesischer_Restsatz
// Pair a_i to m_i
fun chineseRemainder(input: List<Pair<Int, Int>>): Long {
    val M = input.fold(1L, { acc, (_, m_i) -> lcm(acc, m_i.toLong()) })
    var res = input.map { (a_i, m_i) ->
        val M_i = M / m_i
        val (_, r_i, s_i) = gcdExt(m_i.toLong(), M_i)
        val e_i = s_i * M_i
        a_i.toLong() * e_i
    }.sum()

    var resmod = res
    if (res < 0) {
        while (resmod < 0) {
            resmod += M
        }
    } else {
        while (resmod + M > 0) {
            resmod -= M
        }
    }
    return resmod
}


fun primeFactors(n: Int): Map<Int, Int> {
    val res = mutableMapOf<Int, Int>()
    fun inc(j: Int) {
        res[j] = (res[j] ?: 0) + 1
    }

    var rem = n
    while (rem > 1) {
        if (rem % 2 == 0) {
            inc(2)
            rem /= 2
            continue
        }
        var searchFactor = 3
        var found = false
        while (searchFactor <= sqrt(rem.toDouble())) {
            if (rem % searchFactor == 0) {
                inc(searchFactor)
                rem /= searchFactor
                found = true
                break
            }
            searchFactor += 2
        }
        if (!found) {
            inc(rem)
            break
        }
    }
    return res

}
