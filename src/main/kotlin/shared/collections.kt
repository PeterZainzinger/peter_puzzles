package shared

fun <T> Collection<T>.fold1(operation: (acc: T, T) -> T): T? = when (isEmpty()) {
    true -> null
    else -> toList().subList(1, size).fold(this.first(), operation)
}
