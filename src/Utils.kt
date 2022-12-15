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
        val distance = maxOf(abs(x - other.x), abs(y - other.y))
        return (1..distance).scan(this) { last, _ -> Coordinate2D(last.x + dx, last.y + dy) }
    }

    companion object {
        val directions = listOf(Coordinate2D(0, 1), Coordinate2D(0, -1), Coordinate2D(1, 0), Coordinate2D(-1, 0))
    }
}

infix fun Int.towards(to: Int) = IntProgression.fromClosedRange(this, to, step = if (this > to) -1 else 1)