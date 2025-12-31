package aoc16

import measuredNano
import java.io.File
import kotlin.text.indices

object Day6 {

    val input = File("inputs/aoc16/day6.txt")
        .readLines()
        .let { input ->
            input[0].indices.map { id ->
                input.map { it[id] }
                    .groupBy { it }
                    .map { it.key to it.value.size }
                    .sortedBy { it.second }
            }
        }

    fun part1() = input.joinToString("") { c -> c.last().first.toString() }

    fun part2() = input.joinToString("") { c -> c.first().first.toString() }

}

fun main() {
    measuredNano { Day6.part1() }
    measuredNano { Day6.part2() }
}