import kotlin.math.abs
import kotlin.math.sign

fun main() {
    val input = readInput("day09_input")

    val moves = input.asSequence()
        .map { it.substringBefore(' ').single() to it.substringAfter(' ').toInt() }

    val directions = mapOf(
        'U' to Coordinate2D(0, 1),
        'D' to Coordinate2D(0, -1),
        'L' to Coordinate2D(-1, 0),
        'R' to Coordinate2D(1, 0)
    )

    fun countVisitedTailPositions(knotCount: Int): Int {
        val visited = mutableSetOf<Coordinate2D>()
        val knots = MutableList(knotCount) { Coordinate2D(x = 0, y = 0) }
        moves.forEach { (d, r) ->
            repeat(r) {
                knots[0] += directions.getValue(d)
                knots.indices.windowed(2) { (head, tail) ->
                    if (!(knots[tail] isAdjacentTo knots[head])) {
                        knots[tail] = knots[tail] movedTowards knots[head]
                    }
                }
                visited.add(knots.last())
            }
        }
        return visited.size
    }

    val result1 = countVisitedTailPositions(2)
    println(result1)

    val result2 = countVisitedTailPositions(10)
    println(result2)
}

private infix fun Coordinate2D.isAdjacentTo(other: Coordinate2D) = abs(other.x - x) < 2 && abs(other.y - y) < 2

private infix fun Coordinate2D.movedTowards(other: Coordinate2D) =
    this + Coordinate2D((other.x - x).sign, (other.y - y).sign)