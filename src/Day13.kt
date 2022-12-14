fun main() {
    val input = readInputText("day13_input")

    val packetPairs = input.splitToSequence("\n\n")
        .map { it.split("\n") }
        .map { (left, right) -> PacketValue(left) to PacketValue(right) }

    val result1 = packetPairs.mapIndexed { index, (left, right) -> index + 1 to (left compareTo right) }
        .sumOf { (index, result) -> if (result == -1) index else 0 }
    println(result1)

    val result2 = run {
        val firstDividerPacket = PacketValue("[[2]]")
        val secondDividerPacket = PacketValue("[[6]]")
        val sortedPackets = packetPairs.flatMap { it.toList() }
            .plus(listOf(firstDividerPacket, secondDividerPacket)).sorted()
        (sortedPackets.indexOf(firstDividerPacket) + 1) * (sortedPackets.indexOf(secondDividerPacket) + 1)
    }
    println(result2)
}

sealed interface PacketValue : Comparable<PacketValue> {
    companion object {
        operator fun invoke(input: String) = when (val i = input.toIntOrNull()) {
            null -> ListValue(input)
            else -> IntegerValue(i)
        }
    }

    data class IntegerValue(val value: Int) : PacketValue {
        override infix fun compareTo(other: PacketValue): Int = when (other) {
            is IntegerValue -> this.value compareTo other.value
            is ListValue -> ListValue(this) compareTo other
        }
    }

    data class ListValue(val values: List<PacketValue>) : PacketValue {
        constructor(vararg values: PacketValue) : this(values.toList())

        override infix fun compareTo(other: PacketValue): Int = when (other) {
            is IntegerValue -> this compareTo ListValue(other)
            is ListValue -> this.values.zip(other.values)
                .map { (left, right) -> left compareTo right }
                .filterNot { it == 0 }
                .firstOrNull() ?: (this.values.size compareTo other.values.size)
        }

        companion object {
            operator fun invoke(input: String): ListValue {
                val content = input.substring(1 until input.lastIndex)
                if (content.isEmpty()) return ListValue()

                val values = buildList {
                    var current = ""
                    var level = 0
                    for (c in content) {
                        when (c) {
                            '[' -> level++
                            ']' -> level--
                        }
                        if (c == ',' && level == 0) {
                            add(PacketValue(current))
                            current = ""
                            continue
                        }
                        current += c
                    }
                    add(PacketValue(current))
                }
                return ListValue(values)
            }
        }
    }
}