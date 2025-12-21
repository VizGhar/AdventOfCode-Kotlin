package aoc24

import measured
import java.io.File

object Day25 {

    val input = File("inputs/aoc24/d25.txt")
        .readLines()
        .chunked(8)
        .map { it.filter { it.isNotEmpty() } }

    val locks = input.filter { it[0][0] == '#' }.map { lock -> lock[0].indices.map { x -> lock.indexOfFirst { it[x] == '.' } } }

    val keys = input.filter { it[0][0] == '.' }.map { lock -> lock[0].indices.map { x -> lock.indexOfFirst { it[x] == '#' } } }

    fun part1() = locks.sumOf { lock ->
        keys.count { key -> lock.indices.all { x -> lock[x] <= key[x] } }
    }

}

fun main() {
    measured { Day25.part1() }
}
