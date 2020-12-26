package shared

fun <T> Collection<T>.fold1(operation: (acc: T, T) -> T): T? = when (isEmpty()) {
    true -> null
    else -> toList().subList(1, size).fold(this.first(), operation)
}

@JvmName("productDouble")
fun Iterable<Double>.product() = fold(1.0, { acc, i -> acc * i })

@JvmName("productLong")
fun Iterable<Long>.product() = fold(1L, { acc, i -> acc * i })

@JvmName("productInt")
fun Iterable<Int>.product() = fold(1, { acc, i -> acc * i })
