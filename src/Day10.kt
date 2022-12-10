fun main() {
    val input = readInput("day10_input")

    val cycles = input.asSequence()
        .map { it.substringAfter(' ').toIntOrNull() }
        .flatMap { if (it != null) listOf(0, it) else listOf(0) }
        .scan(1, Int::plus)
        .mapIndexed { index, register -> index + 1 to register }

    val result1 = cycles
        .filter { (cycle, _) -> cycle % 40 == 20 }
        .sumOf { (cycle, register) -> cycle * register }
    println(result1)

    val result2 = cycles.fold("") { image, (cycle, register) ->
        image + if ((cycle - 1) % 40 in register - 1..register + 1) "#" else "."
    }.dropLast(1).chunked(40).joinToString("\n")
    println(result2)
}