fun main() {
    val input = readInputAsLines("day12_input")
    val heightmap =
        input.flatMapIndexed { i, row -> row.mapIndexed { j, height -> Coordinate2D(i, j) to height } }.toMap()

    fun reach(startElevation: Char, destinationElevation: Char): Int {
        val (start, _) = heightmap.entries.first { (_, h) -> h == startElevation }

        val distances = heightmap.keys.associateWith { Int.MAX_VALUE }.toMutableMap()
            .apply { this[start] = 0 }
        val possibleMoves = mutableListOf(start)

        while (possibleMoves.isNotEmpty()) {
            val curr = possibleMoves.removeFirst()
            curr.neighbours()
                .filter { n -> n in heightmap && heightmap.getElevation(n) - heightmap.getElevation(curr) <= 1 } // change to >= -1 for second part
                .forEach { neighbour ->
                    val distance = distances.getValue(curr) + 1

                    if (heightmap[neighbour] == destinationElevation)
                        return distance

                    if (distance < distances.getValue(neighbour)) {
                        distances[neighbour] = distance
                        possibleMoves.add(neighbour)
                    }
                }
        }

        error("Couldn't reach destination.")
    }

    val result1 = reach(startElevation = 'S', destinationElevation = 'E')
    println(result1)

//    val result2 = reach(startElevation = 'E', destinationElevation = 'a')
//    println(result2)
}

private fun Map<Coordinate2D, Char>.getElevation(coordinate: Coordinate2D) =
    when (val c = getValue(coordinate)) {
        'S' -> 'a'
        'E' -> 'z'
        else -> c
    }