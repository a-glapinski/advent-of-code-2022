fun main() {
    val input = readInputAsLines("day08_input_test")
    val grid = input.map { it.map(Char::digitToInt) }

    val result1 = flyOver(grid,
        lookAround = { current, direction -> direction.all { it < current } },
        calculate = { directions -> if (directions.any { it }) 1 else 0 }
    ).sum()
    println(result1)

    val result2 = flyOver(grid,
        lookAround = { current, direction ->
            (direction.indexOfFirst { it >= current } + 1).takeIf { it != 0 } ?: direction.size
        },
        calculate = { it.reduce(Int::times) }
    ).max()
    println(result2)
}

fun <T> flyOver(grid: List<List<Int>>, lookAround: (Int, List<Int>) -> T, calculate: (List<T>) -> Int): List<Int> =
    with(grid.indices) {
        flatMap { i ->
            map { j ->
                val current = grid[i][j]
                val left = lookAround(current, grid[i].slice(j - 1 downTo 0))
                val right = lookAround(current, grid[i].slice(j + 1 until grid.size))
                val up = lookAround(current, grid.slice(i - 1 downTo 0).map { it[j] })
                val down = lookAround(current, grid.slice(i + 1 until grid.size).map { it[j] })
                calculate(listOf(left, right, up, down))
            }
        }
    }