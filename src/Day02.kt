fun main() {
    val input = readInputAsLines("day02_input")

    val m = mapOf('A' to 0, 'B' to 1, 'C' to 2, 'X' to 0, 'Y' to 1, 'Z' to 2)

    val result1 = input.map { m.getValue(it[0]) to m.getValue(it[2]) }
        .sumOf { (s1, s2) -> s2 + 1 + (s2 - s1 + 1).mod(3) * 3 }
    println(result1)

    val result2 = input.map { m.getValue(it[0]) to m.getValue(it[2]) }
        .sumOf { (s, o) -> (s + o - 1).mod(3) + 1 + o * 3 }
    println(result2)
}