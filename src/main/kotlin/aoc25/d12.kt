package aoc25

import java.io.File

object Day12 {
    data class Case(val width: Int, val height: Int, val presentCounts: List<Int>)

    val file = File("inputs/aoc25/d12.txt").readLines()
    val cases = file.takeLastWhile { it.isNotBlank() }.map {
        Case(
            width = it.substringBefore("x").toInt(),
            height = it.substringAfter("x").substringBefore(":").toInt(),
            presentCounts = it.substringAfter(": ").split(" ").map { it.toInt() }
        )
    }

    fun part1() =
        cases.count { case ->
            case.presentCounts.sum() * 9 <= case.width * case.height
        }
}