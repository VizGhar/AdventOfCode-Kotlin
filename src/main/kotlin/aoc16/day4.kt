package aoc16

import measuredNano
import java.io.File

object Day4 {

    val input = File("inputs/aoc16/day4.txt")
        .readLines()

    fun part1() =
        input.sumOf {
            val checksum = it.substringAfter("[").dropLast(1)
            val sectorId = it.substringBefore("[").substringAfterLast("-").toInt()
            val letters = it.substringBeforeLast("-")
                .replace("-", "")
                .toCharArray()
                .sorted()
                .groupBy { it }
                .map { it.key to it.value.size }
                .sortedByDescending { it.second }
                .take(5)
                .joinToString("") { it.first.toString() }
            if (checksum == letters) sectorId else 0
        }

    fun part2() =
        input.firstNotNullOf {
            val sectorId = it.substringBefore("[").substringAfterLast("-").toInt()
            val letters = it.substringBeforeLast("-")
            val result = letters.toCharArray().joinToString("") { c ->
                if (c == '-') ' ' else {
                    val newC = c + sectorId % 26
                    if (newC in 'a'..'z') newC else c + sectorId % 26 - 26
                }.toString()
            }
            sectorId.takeIf { "north" in result }
        }
}

fun main() {
    measuredNano { Day4.part1() }
    measuredNano { Day4.part2() }
}