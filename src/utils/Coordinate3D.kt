package utils

data class Coordinate3D(val x: Int, val y: Int, val z: Int) {
    operator fun plus(other: Coordinate3D) = copy(x = x + other.x, y = y + other.y, z = z + other.z)

    fun neighbours() = directions.map(::plus)

    companion object {
        val directions = listOf(
            Coordinate3D(-1, 0, 0), Coordinate3D(1, 0, 0),
            Coordinate3D(0, -1, 0), Coordinate3D(0, 1, 0),
            Coordinate3D(0, 0, -1), Coordinate3D(0, 0, 1)
        )
    }
}
