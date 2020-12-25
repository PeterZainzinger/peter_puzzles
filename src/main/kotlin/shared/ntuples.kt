package shared

infix fun <A, B> A.then(that: B): Pair<A, B> = Pair(this, that)
infix fun <A, B, C> Pair<A, B>.then(c: C) = Triple(first, second, c)
