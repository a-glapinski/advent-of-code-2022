package utils

import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInputAsLines(name: String) = File("src", "$name.txt")
    .readLines()

fun readInputAsText(name: String) = File("src", "$name.txt")
    .readText()

operator fun <E> List<E>.component6() = get(5)

infix fun Int.towards(to: Int) = IntProgression.fromClosedRange(this, to, step = if (this > to) -1 else 1)

infix fun IntRange.union(other: IntRange): IntRange =
    when (this.first <= other.last && this.last >= other.first) {
        true -> IntRange(minOf(this.first, other.first), maxOf(this.last, other.last))
        false -> IntRange.EMPTY
    }