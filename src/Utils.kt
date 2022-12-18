import java.io.File
import kotlin.math.abs
import kotlin.math.sign

/**
 * Reads lines from the given input txt file.
 */
fun readInputAsLines(name: String) = File("src", "$name.txt")
    .readLines()

fun readInputAsText(name: String) = File("src", "$name.txt")
    .readText()

operator fun <E> List<E>.component6() = get(5)

data class Coordinate2D(val x: Int, val y: Int) {
    operator fun plus(other: Coordinate2D) = copy(x = x + other.x, y = y + other.y)

    fun neighbours() = directions.map(::plus)

    infix fun lineTo(other: Coordinate2D): List<Coordinate2D> {
        val dx = (other.x - x).sign
        val dy = (other.y - y).sign
        val steps = maxOf(abs(x - other.x), abs(y - other.y))
        return (1..steps).scan(this) { last, _ -> Coordinate2D(last.x + dx, last.y + dy) }
    }

    infix fun distanceTo(other: Coordinate2D) = abs(x - other.x) + abs(y - other.y)

    companion object {
        val directions = listOf(Coordinate2D(0, 1), Coordinate2D(0, -1), Coordinate2D(1, 0), Coordinate2D(-1, 0))
    }
}

infix fun Int.towards(to: Int) = IntProgression.fromClosedRange(this, to, step = if (this > to) -1 else 1)

infix fun IntRange.union(other: IntRange): IntRange =
    when (this.first <= other.last && this.last >= other.first) {
        true -> IntRange(minOf(this.first, other.first), maxOf(this.last, other.last))
        false -> IntRange.EMPTY
    }