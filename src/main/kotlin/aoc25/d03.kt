package aoc25

import measured
import java.io.File

object Day3 {

    val input = File("inputs/aoc25/d3.txt").readLines()

    fun solve(picks: Int) = input.sumOf { line ->
        var remainingPicks = picks
        var start = 0
        var result = ""
        do {
            val end = line.length - remainingPicks
            val best = line.substring(start..end).max()
            result += best
            start += line.substring(start..end).indexOfFirst { it == best } + 1
        } while (--remainingPicks != 0)
        result.toLong()
    }

    fun part1() = solve(2)

    fun part2() = solve(12)
}

fun main() {
    measured { Day3.part1() }
    measured { Day3.part2() }
}
