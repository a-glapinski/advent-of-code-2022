import Day20.moveItem
import utils.cycle
import utils.readInputAsLines
import java.util.*

fun main() {
    val numbers = readInputAsLines("day20_input").map { it.toLong() }

    fun mix(decryptionKey: Int = 1, repeatCount: Int = 1): List<Long> {
        val actualNumbers = numbers.map { it * decryptionKey }.withIndex()
        val mixed = actualNumbers.toMutableList()
        repeat(repeatCount) {
            actualNumbers.forEach { number ->
                val index = mixed.indexOf(number)
                mixed.moveItem(index, (index + number.value).mod(mixed.size - 1))
            }
            Collections.rotate(mixed, -1)
        }
        return mixed.map { it.value }
    }

    fun sumOfGroveCoordinates(mixed: List<Long>) = mixed.cycle()
        .dropWhile { it != 0L }.take(3001)
        .filterIndexed { index, _ -> index % 1000 == 0 }.sum()

    println(sumOfGroveCoordinates(mix()))
    println(sumOfGroveCoordinates(mix(decryptionKey = 811589153, repeatCount = 10)))
}

object Day20 {
    fun <T> MutableList<T>.moveItem(sourceIndex: Int, targetIndex: Int) = when {
        sourceIndex <= targetIndex -> Collections.rotate(subList(sourceIndex, targetIndex + 1), -1)
        else -> Collections.rotate(subList(targetIndex, sourceIndex + 1), 1)
    }
}