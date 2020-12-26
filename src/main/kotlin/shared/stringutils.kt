package shared

val alphabet = "abcdefghijklmniopqrstuvwxyz".toSet()

fun String.splitLines() = split("\n")
fun String.splitEmptyLines() = split("\n\n")
fun String.splitComma() = split(",")
