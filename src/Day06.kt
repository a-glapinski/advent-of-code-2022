fun main() {
    val input = readInputAsText("day06_input")

    val result1 = input
        .windowedSequence(4) { it.toSet() }
        .indexOfFirst { it.size == 4 } + 4
    println(result1)

    val result2 = input
        .windowedSequence(14) { it.toSet() }
        .indexOfFirst { it.size == 14 } + 14
    println(result2)
}