package aoc24

import java.io.File

object Day3 {

    private val input = File("inputs/aoc24/d3.txt")
        .readLines()
        .let { "do()${it.joinToString("")}do()" }

    private val multiplications by lazy { "mul\\((\\d+),(\\d+)\\)".toRegex().findAll(input) }

    private fun multiply(filter: Sequence<MatchResult>): Long = filter.sumOf {
        it.groupValues[1].toLong() * it.groupValues[2].toLong()
    }

    fun part1() = multiply(multiplications).toString()

    fun part2() = "do\\(\\)|don't\\(\\)".toRegex().findAll(input)
        .zipWithNext()
        .mapNotNull { (prev, curr) -> (prev.range.first..curr.range.first).takeIf { prev.value == "do()" }?.let {
            multiply(multiplications.filter { mul -> it.contains(mul.range.first) })
        } }.sum()
        .toString()

}
