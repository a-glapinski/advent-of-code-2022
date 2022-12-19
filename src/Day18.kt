import utils.Coordinate3D
import utils.readInputAsLines

fun main() {
    val input = readInputAsLines("day18_input_test")
    val cubes = input.map { it.split(',') }
        .map { (x, y, z) -> Coordinate3D(x.toInt(), y.toInt(), z.toInt()) }.toSet()

    fun part1() = cubes.sumOf { cube -> cube.neighbours().count { it !in cubes } }
    println(part1())

    fun part2(): Int {
        val xBounds = (cubes.minOf { it.x } - 1)..(cubes.maxOf { it.x } + 1)
        val yBounds = (cubes.minOf { it.y } - 1)..(cubes.maxOf { it.y } + 1)
        val zBounds = (cubes.minOf { it.z } - 1)..(cubes.maxOf { it.z } + 1)

        val toVisit = mutableListOf(Coordinate3D(xBounds.first, yBounds.first, zBounds.first))
        val visited = mutableSetOf<Coordinate3D>()

        var surfaceArea = 0
        while (toVisit.isNotEmpty()) {
            val current = toVisit.removeFirst()
            if (current !in visited) {
                current.neighbours()
                    .filter { (x, y, z) -> x in xBounds && y in yBounds && z in zBounds }
                    .forEach { if (it in cubes) surfaceArea++ else toVisit.add(it) }
                visited.add(current)
            }
        }
        return surfaceArea
    }
    println(part2())
}