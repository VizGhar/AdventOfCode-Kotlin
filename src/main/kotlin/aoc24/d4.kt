package aoc24

import java.io.File

object Day4 {

    private val input = File("inputs/aoc24/d4.txt").readLines()

    fun part1() = listOf(1 to 0, 0 to 1, 1 to 1, 1 to - 1).sumOf { (dx, dy) ->
        (maxOf(-dy * 3, 0)..<input.size - 3 * maxOf(dy, 0)).sumOf { y ->
            (maxOf(-dx * 3, 0)..<input[0].length - 3 * maxOf(dx, 0)).count { x ->
                (0..3).map { d -> input[y + d * dy][x + d * dx] }.joinToString("") in listOf("XMAS", "SAMX")
            }
        }
    }

    fun part2() = (1..<input.size - 1).sumOf { y ->
        (1..<input[0].length - 1).count { x ->
            input[y][x] == 'A' &&
                    "${input[y-1][x-1]}${input[y][x]}${input[y+1][x+1]}" in listOf("MAS", "SAM") &&
                    "${input[y-1][x+1]}${input[y][x]}${input[y+1][x-1]}" in listOf("MAS", "SAM")
        }
    }
}
