package aoc24

import java.io.File
import kotlin.math.abs

object Day2 {

    private val inputs = File("inputs/aoc24/d2.txt").readLines().map { it.split(" ").map { it.toInt() } }

    private fun List<Int>.isOk() = (sorted() == this || sortedDescending() == this) && windowed(2).all { abs(it[0] - it[1]) in 1..3 }

    fun part1() = inputs.count { it.isOk() }

    fun part2() = inputs.count { list ->
        list.indices.any { index -> (list.take(index) + list.drop(index + 1)).isOk() }
    }
    
}
