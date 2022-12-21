import utils.cycle
import utils.readInputAsLines

fun main() {
    val numbers = readInputAsLines("day20_input").map { it.toLong() }

    fun mix(decryptionKey: Int = 1, repeatCount: Int = 1): List<Long> {
        val actualNumbers = numbers.map { it * decryptionKey }.withIndex()
        val mixed = actualNumbers.toMutableList()
        repeat(repeatCount) {
            actualNumbers.forEach { number ->
                val index = mixed.indexOf(number)
                val item = mixed.removeAt(index)
                mixed.add((index + number.value).mod(mixed.size), item)
            }
        }
        return mixed.map { it.value }
    }

    fun sumOfGroveCoordinates(mixed: List<Long>) = mixed.cycle()
        .dropWhile { it != 0L }.take(3001)
        .filterIndexed { index, _ -> index % 1000 == 0 }.sum()

    println(sumOfGroveCoordinates(mix()))
    println(sumOfGroveCoordinates(mix(decryptionKey = 811589153, repeatCount = 10)))
}