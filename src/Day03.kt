fun main() {
    val input = readInput("day03_input")

    val priorities =
        ('a'..'z').associateWith { c -> c.code - 96 } + ('A'..'Z').associateWith { c -> c.code - 64 + 26 }

    val result1 = input.asSequence()
        .map { it.substring(0 until it.length / 2) to it.substring(it.length / 2) }
        .map { (a, b) -> a.toSet() intersect b.toSet() }
        .sumOf { priorities.getValue(it.single()) }
    println(result1)

    val result2 = input.asSequence()
        .chunked(3) { it.map { s -> s.toSet() } }
        .map { it[0] intersect it[1] intersect it[2] }
        .sumOf { priorities.getValue(it.single()) }
    println(result2)
}