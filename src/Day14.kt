import Day14.move

fun main() {
    val input = readInputAsLines("day14_input")

    val caveMap = input.flatMap { path ->
        path.splitToSequence(" -> ")
            .map { Coordinate2D(x = it.substringBefore(',').toInt(), y = it.substringAfter(',').toInt()) }
            .zipWithNext { left, right -> left lineTo right }
            .flatten()
    }.toSet()

    val sandSource = Coordinate2D(500, 0)

    fun part1(): Int {
        val floorLevel = caveMap.maxOf(Coordinate2D::y)
        val resting = mutableSetOf<Coordinate2D>()
        var current = sandSource
        while (current.y < floorLevel) {
            current = current.move(caveMap, resting) ?: sandSource.also { resting.add(current) }
        }
        return resting.size
    }
    println(part1())

    fun part2(): Int {
        val floorLevel = caveMap.maxOf(Coordinate2D::y) + 2
        val resting = mutableSetOf<Coordinate2D>()
        var current = sandSource
        while (sandSource !in resting) {
            val next = current.move(caveMap, resting)
            current = when {
                next == null || next.y == floorLevel -> sandSource.also { resting.add(current) }
                else -> next
            }
        }
        return resting.size
    }
    println(part2())
}

private object Day14 {
    private val directions = sequenceOf(Coordinate2D(0, 1), Coordinate2D(-1, 1), Coordinate2D(1, 1))

    fun Coordinate2D.move(caveMap: Set<Coordinate2D>, resting: Set<Coordinate2D>) =
        directions.map(::plus).firstOrNull { it !in caveMap && it !in resting }
}