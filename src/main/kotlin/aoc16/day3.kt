package aoc16

import measuredNano
import java.io.File

object Day3 {

    val input = File("inputs/aoc16/day3.txt")
        .readLines()
        .map { it.split(" ").mapNotNull { if (it.isNotEmpty()) it.toInt() else null } }

    fun isTriangle(s1: Int, s2: Int, s3: Int) = when {
        s1 >= s2 && s1 >= s3 -> s2 + s3 > s1
        s2 >= s1 && s2 >= s3 -> s1 + s3 > s2
        else -> s1 + s2 > s3
    }

    fun part1() = input.count { isTriangle(it[0], it[1], it[2]) }
    fun part2() = input.chunked(3)
        .sumOf { chunk ->
            if (isTriangle(chunk[0][0], chunk[1][0], chunk[2][0])) 1 else 0 +
            if (isTriangle(chunk[0][1], chunk[1][1], chunk[2][1])) 1 else 0 +
            if (isTriangle(chunk[0][2], chunk[1][2], chunk[2][2])) 1 else 0
        }

}

fun main() {
    measuredNano { Day3.part1() }
    measuredNano { Day3.part2() }
}