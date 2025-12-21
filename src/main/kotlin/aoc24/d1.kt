package aoc24

import java.io.File
import kotlin.math.abs

object Day1 {

    private val inputs = File("inputs/aoc24/d1.txt").readLines().map { it.split("   ").map { it.toInt() }.let { it[0] to it[1] } }.unzip()

    fun part1() = inputs.let { (l1, l2) ->
        l1.sorted().zip(l2.sorted()).sumOf { abs(it.first - it.second) }
    }.toString()

    fun part2() = inputs.let { (l1, l2) ->
        l1.sumOf { a -> a * l2.count { b -> a == b } }
    }.toString()

}