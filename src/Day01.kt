fun main() {
    fun part1(input: List<String>) =
        sumCaloriesByElves(input).max()

    fun part2(input: List<String>) =
        sumCaloriesByElves(input).sortedDescending().take(3).sum()

    val input = readInput("day01_input")

    val result1 = part1(input)
    println(result1)

    val result2 = part2(input)
    println(result2)
}

fun sumCaloriesByElves(input: List<String>) = sequence {
    var sum = 0
    input.forEach { s ->
        when (val i = s.toIntOrNull()) {
            null -> { yield(sum); sum = 0 }
            else -> sum += i
        }
    }
}