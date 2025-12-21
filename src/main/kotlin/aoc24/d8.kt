package aoc24

import java.io.File

object Day8 {

    private data class Position(val x: Int, val y: Int)
    private data class Antenna(val x: Int, val y: Int, val c: Char)

    private data class Input(
        val width: Int,
        val height: Int,
        val antennas: Map<Char, List<Antenna>>
    )

    private val input = File("inputs/aoc24/d8.txt").readLines().let {
        Input(it[0].length, it.size, it.mapIndexed { y, s ->
            s.mapIndexedNotNull { x, c -> Antenna(x, y, c).takeIf { it.c != '.' } }
        }.flatten().groupBy { it.c })
    }

    private inline fun countAntinodes(anitinodes: (a: Antenna, b: Antenna, dx: Int, dy: Int) -> List<Position>) =
        input.antennas.map { (_, antennas) ->
            (0..<antennas.size - 1).map { i ->
                (i + 1..<antennas.size).map { j ->
                    anitinodes(antennas[i], antennas[j], antennas[i].x - antennas[j].x, antennas[i].y - antennas[j].y)
                }
            }.flatten().flatten().filter {
                it.x in 0..<input.width && it.y in 0..<input.height
            }
        }.flatten().distinct().size


    fun part1() = countAntinodes { a, b, dx, dy -> listOf(Position(a.x + dx, a.y + dy), Position(b.x - dx, b.y - dy)) }

    fun part2() = countAntinodes { a, b, dx, dy ->
        mutableListOf<Position>().apply {
            var (x1, y1) = a
            while (x1 in 0..<input.width && y1 in 0..<input.height) {
                add(Position(x1,y1)); x1 += dx; y1 += dy
            }
            var (x2, y2) = b
            while (x2 in 0..<input.width && y2 in 0..<input.height) {
                add(Position(x2, y2)); x2 -= dx; y2 -= dy
            }
        }
    }
}
