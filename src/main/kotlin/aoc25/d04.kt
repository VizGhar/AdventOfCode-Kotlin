package aoc25

import measured
import java.io.File

object Day4 {

    fun isRoll(map: List<String>, x: Int, y: Int) =
        runCatching { map[y][x] == '@' }.getOrNull() ?: false

    fun neighbors(map: List<String>, x: Int, y: Int) =
        (-1..1).sumOf { dx -> (-1..1).count { dy -> isRoll(map, x + dx, y + dy) } }

    val map = File("inputs/aoc25/d4.txt").readLines()

    fun part1() = (0..<map.size).sumOf { y ->
        (0..<map[0].length).count { x ->
            isRoll(map, x, y) && neighbors(map, x, y) < 5
        }
    }

    fun part2(): String {
        var count = 0
        var map = map
        while (true) {
            val toRemove = (0..<map.size)
                .map { y ->
                    (0..<map[0].length)
                        .mapNotNull { x ->
                            if (isRoll(map, x, y) && neighbors(map, x, y) < 5) x to y else null
                        }
                }.flatten()

            map = map.mapIndexed { y, line ->
                line.mapIndexed { x, c -> if (x to y in toRemove) '.' else c }.joinToString("")
            }

            count += toRemove.size
            if (toRemove.isEmpty()) break
        }
        return count.toString()
    }
}

fun main() {
    measured { Day4.part1() }
    measured { Day4.part2() }
}
