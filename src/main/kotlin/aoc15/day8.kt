package aoc15

import measuredNano
import java.io.File

object Day8 {

    val input = File("inputs/aoc15/d08.txt")
        .readLines()

    fun part1(): Int = input.sumOf { line ->
        var adjusted = line.substring(1, line.length - 1)
        var count = 0
        while (adjusted.isNotEmpty()) {
            count++
            when {
                adjusted[0] == '\\' && adjusted.length > 1 && adjusted[1] == '\\' -> adjusted = adjusted.drop(2)
                adjusted[0] == '\\' && adjusted.length > 1 && adjusted[1] == '"' -> adjusted = adjusted.drop(2)
                adjusted[0] == '\\' && adjusted.length > 3 && adjusted[1] == 'x' && adjusted.substring(2, 4).toIntOrNull(16) != null -> adjusted = adjusted.drop(4)
                else -> adjusted = adjusted.drop(1)
            }
        }
        line.length - count
    }

    fun part2() = input.sumOf { line ->
        var result = ""
        var adjusted = line
        while (adjusted.isNotEmpty()) {
            when {
                adjusted[0] == '\\' -> result += """\\"""
                adjusted[0] == '"' -> result += """\""""
                adjusted[0] == '\\' && adjusted.length > 3 && adjusted[1] == 'x' && adjusted.substring(2, 4).toIntOrNull(16) != null -> adjusted = adjusted.drop(4)
                else -> result += adjusted[0]
            }
            adjusted = adjusted.drop(1)
        }
        result = "\"$result\""
        result.length - line.length
    }

}

fun main() {
    measuredNano { Day8.part1() }
    measuredNano { Day8.part2() }
}