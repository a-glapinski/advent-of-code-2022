import Day15.rangesFor
import utils.Coordinate2D
import utils.readInputAsLines
import utils.union
import kotlin.math.abs

fun main() {
    val input = readInputAsLines("day15_input")
    val regex = """x=(-?[0-9]+), y=(-?[0-9]+).*x=(-?[0-9]+), y=(-?[0-9]+)""".toRegex()

    val sensorReports = input
        .mapNotNull { regex.find(it)?.destructured }
        .map { (x1, y1, x2, y2) -> Coordinate2D(x1.toInt(), y1.toInt()) to Coordinate2D(x2.toInt(), y2.toInt()) }
    val beacons = sensorReports.map { it.second }.toSet()

    fun part1(rowNumber: Int) = sensorReports.rangesFor(rowNumber).let { ranges ->
        IntRange(ranges.minOf { it.first }, ranges.maxOf { it.last }).count() - beacons.count { it.y == rowNumber }
    }
    println(part1(rowNumber = 2_000_000))

    fun part2(): Long {
        for (rowNumber in 0..4_000_000) {
            sensorReports.rangesFor(rowNumber).reduce { acc, range ->
                (acc union range).takeUnless { it.isEmpty() } ?: return (acc.last + 1) * 4_000_000L + rowNumber
            }
        }
        error("Distress beacon not found.")
    }
    println(part2())
}

object Day15 {
    fun List<Pair<Coordinate2D, Coordinate2D>>.rangesFor(rowNumber: Int): List<IntRange> =
        mapNotNull { (sensor, beacon) ->
            val distance = sensor distanceTo beacon
            val first = sensor.x - distance + abs(rowNumber - sensor.y)
            val last = sensor.x + distance - abs(rowNumber - sensor.y)
            IntRange(first, last).takeUnless { it.isEmpty() }
        }.sortedBy { it.first }
}