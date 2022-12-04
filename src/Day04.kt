import kotlin.math.max

fun main() {
    val input = readInput("day04_input")

    val assignments = input.asSequence()
        .map { it.split(',', '-') }
        .map { (aFirst, aLast, bFirst, bLast) ->
            aFirst.toInt()..aLast.toInt() to bFirst.toInt()..bLast.toInt()
        }

    val result1 = assignments
        .count { (a, b) -> (a union b).count() == max(a.count(), b.count()) }
    println(result1)

    val result2 = assignments
        .count { (a, b) -> (a intersect b).isNotEmpty() }
    println(result2)
}