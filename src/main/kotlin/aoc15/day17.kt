package aoc15

import measuredNano
import java.io.File

object Day17 {

    val input = File("inputs/aoc15/d17.txt").readLines().map { it.toInt() }

    val memo = mutableMapOf<Pair<Int, Int>, Long>()

    fun recurse(
        remaining: Int,
        used: List<Int> = emptyList(),
        index: Int = 0
    ): Long {
        val key = remaining to index
        return memo.getOrPut(key) {
            when {
                remaining < 0 -> 0L
                remaining == 0 -> 1L
                else -> (index..<input.size).sumOf { recurse(remaining - input[it], used + input[it], it + 1) }
            }
        }
    }

    fun part1() = recurse(150)

    var best = Int.MAX_VALUE
    var results = 0

    fun recurse2(
        remaining: Int,
        used: List<Int> = emptyList(),
        index: Int = 0
    ) {
        when {
            remaining < 0 -> { }
            remaining == 0 -> {
                if (used.size < best) {
                    best = used.size
                    results = 1
                } else if (used.size == best) {
                    results++
                }
            }
            else -> (index..<input.size).forEach { recurse2(remaining - input[it], used + input[it], it + 1) }
        }
    }

    fun part2() = recurse2(150).let { results }
}

fun main() {
    measuredNano { Day17.part1() }
    measuredNano { Day17.part2() }
}
