package aoc16

import measuredNano
import java.io.File

object Day2 {

    val input = File("inputs/aoc16/day2.txt").readLines()

    fun solve(
        keypad: List<String>,
        initialPosition: Pair<Int, Int>
    ): String {
        var pos = initialPosition
        return input.map { line ->
            for (c in line) {
                val newPos = when (c) {
                    'L' -> pos.first - 1 to pos.second
                    'U' -> pos.first to pos.second - 1
                    'R' -> pos.first + 1 to pos.second
                    'D' -> pos.first to pos.second + 1
                    else -> throw IllegalStateException()
                }
                if (
                    newPos.second in 0..<keypad.size &&
                    newPos.first in 0..<keypad[0].length &&
                    keypad[newPos.second][newPos.first] != ' '
                ) {
                    pos = newPos
                }
            }
            keypad[pos.second][pos.first]
        }.joinToString("")
    }

    fun part1() = solve(listOf("123", "456", "789",), 1 to 1)

    fun part2() = solve(listOf("  1  ", " 234 ", "56789", " ABC ", "  D  ",), 0 to 2)

}

fun main() {
    measuredNano { Day2.part1() }
    measuredNano { Day2.part2() }
}