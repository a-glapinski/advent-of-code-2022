import Day21.MonkeyJob
import utils.readInputAsLines

fun main() {
    val input = readInputAsLines("day21_input")

    val monkeys = input.map { it.split(": ", " ") }
        .map {
            val name = it[0]
            val number = it[1].toLongOrNull()
            if (number != null) return@map name to MonkeyJob { number }

            val firstMonkey = it[1]
            val secondMonkey = it[3]
            val operation: (Long, Long) -> Long = when (it[2]) {
                "+" -> Long::plus
                "-" -> Long::minus
                "*" -> Long::times
                "/" -> Long::div
                else -> error("Unsupported operation")
            }
            name to MonkeyJob { monkeys ->
                operation(monkeys.getValue(firstMonkey).yell(monkeys), monkeys.getValue(secondMonkey).yell(monkeys))
            }
        }.toMap()

    println(monkeys.getValue("root").yell(monkeys))
}

object Day21 {
    fun interface MonkeyJob {
        fun yell(monkeys: Map<String, MonkeyJob>): Long
    }
}