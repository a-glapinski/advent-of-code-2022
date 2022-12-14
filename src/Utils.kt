import java.io.File

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
}