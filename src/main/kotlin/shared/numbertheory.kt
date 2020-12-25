package shared

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
