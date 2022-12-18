import utils.readInputAsText

fun main() {
    val input = readInputAsText("day11_input")
    val monkeys = input.split("\n\n").map { Monkey(note = it.lines()) }

    fun playKeepAway(monkeys: List<Monkey>, roundCount: Int, calculateWorryLevel: (Long, Int) -> Long): Long {
        val modulus = monkeys.map { it.divisor }.reduce(Int::times)

        repeat(roundCount) {
            monkeys.forEach { monkey ->
                monkey.items.forEach { item ->
                    monkey.itemsInspected++
                    val newWorryLevel = calculateWorryLevel(monkey.operation(item), modulus)
                    val thrownTo =
                        if (newWorryLevel % monkey.divisor == 0L) monkey.ifTrueThrowsTo else monkey.ifFalseThrowsTo
                    monkeys[thrownTo].items.add(newWorryLevel)
                }
                monkey.items.clear()
            }
        }

        return monkeys.map { it.itemsInspected.toLong() }.sortedDescending().take(2).reduce(Long::times)
    }

    val result1 = playKeepAway(
        monkeys = monkeys.map { it.copy(items = it.items.toMutableList()) }, roundCount = 20,
        calculateWorryLevel = { item, _ -> item / 3 }
    )
    println(result1)

    val result2 = playKeepAway(
        monkeys = monkeys.map { it.copy(items = it.items.toMutableList()) }, roundCount = 10_000,
        calculateWorryLevel = { item, modulus -> item % modulus }
    )
    println(result2)
}

data class Monkey(
    var itemsInspected: Int = 0,
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val divisor: Int,
    val ifTrueThrowsTo: Int,
    val ifFalseThrowsTo: Int,
) {
    companion object {
        operator fun invoke(note: List<String>): Monkey {
            val operationArg = note[2].substringAfterLast(' ').toLongOrNull()
            val operationOperand = note[2].substringAfter("old ").first()
            return Monkey(
                items = note[1].substringAfter(": ").split(", ").map(String::toLong).toMutableList(),
                operation = { old: Long ->
                    val arg = operationArg ?: old
                    when (operationOperand) {
                        '*' -> old * arg
                        '+' -> old + arg
                        else -> error("Unsupported operation type.")
                    }
                },
                divisor = note[3].takeLastWhile(Char::isDigit).toInt(),
                ifTrueThrowsTo = note[4].takeLastWhile(Char::isDigit).toInt(),
                ifFalseThrowsTo = note[5].takeLastWhile(Char::isDigit).toInt()
            )
        }
    }
}