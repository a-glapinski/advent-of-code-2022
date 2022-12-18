import utils.component6
import utils.readInputAsLines

fun main() {
    val (stacksInput, proceduresInput) = readInputAsLines("day05_input").asSequence()
        .filter { it.isNotBlank() }
        .partition { !it.startsWith("move") }

    val stackIndices = stacksInput.last().withIndex()
        .filter { (_, c) -> c.isDigit() }
        .map { (index, _) -> index }
    val stacks = List(stackIndices.size) { ArrayDeque<Char>() }.apply {
        stacksInput.dropLast(1)
            .map { it.filterIndexed { index, _ -> index in stackIndices } }
            .forEach { level ->
                level.forEachIndexed { index, c ->
                    if (c.isLetter()) get(index).addLast(c)
                }
            }
    }

    val procedures = proceduresInput.asSequence()
        .map { it.split(' ') }
        .map { (_, quantity, _, from, _, to) ->
            Triple(quantity.toInt(), from.toInt(), to.toInt())
        }

    fun part1(): String {
        val s = stacks.map(::ArrayDeque)
        procedures.forEach { (quantity, from, to) ->
            repeat(quantity) {
                s[to - 1].addFirst(s[from - 1].removeFirst())
            }
        }
        return s.map { it.first() }.joinToString("")
    }
    println(part1())

    fun part2(): String {
        val s = stacks.map(::ArrayDeque)
        procedures.forEach { (quantity, from, to) ->
            List(quantity) { s[from - 1].removeFirst() }.asReversed()
                .forEach { s[to - 1].addFirst(it) }
        }
        return s.map { it.first() }.joinToString("")
    }
    println(part2())
}