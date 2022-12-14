fun main() {
    val input = readInputAsLines("day03_input")

    val priorities =
        ('a'..'z').associateWith { c -> c.code - 96 } + ('A'..'Z').associateWith { c -> c.code - 64 + 26 }

    val result1 = input.asSequence()
        .map { it.substring(0 until it.length / 2) to it.substring(it.length / 2) }
        .map { (a, b) -> a.toSet() intersect b.toSet() }
        .sumOf { priorities.getValue(it.single()) }
    println(result1)

    val result2 = input.asSequence()
        .map { it.toSet() }
        .chunked(3)
        .map { (a, b, c) -> a intersect b intersect c }
        .sumOf { priorities.getValue(it.single()) }
    println(result2)
}