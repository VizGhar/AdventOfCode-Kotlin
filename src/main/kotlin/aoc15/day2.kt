package aoc15

import measuredNano
import java.io.File

object Day2 {

    val input = File("inputs/aoc15/d02.txt").readLines().map { it.split("x").map { it.toInt() }.sorted() }

    fun part1() = input.sumOf { it.let { (a, b, c) -> 3 * a * b + 2 * a * c + 2 * b * c } }

    fun part2() = input.sumOf { it.let { (a, b, c) -> 2 * a + 2 * b + a * b * c } }

}

fun main() {
    measuredNano { Day2.part1() }
    measuredNano { Day2.part2() }
}