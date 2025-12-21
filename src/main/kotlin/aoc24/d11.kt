package aoc24

import java.io.File

object Day11 {

    private val input = File("inputs/aoc24/d11.txt").readText().split(" ")

    private val memo = mutableMapOf<Pair<String, Int>, Long>()

    private fun String.half() = substring(0, length / 2).toLong().toString()
    private fun String.secondHalf() = substring(length / 2).toLong().toString()

    private fun countStones(number: String, iterations: Int = 25): Long {
        val key = number to iterations
        memo[key]?.let { return it }
        return when {
            iterations == 0 -> 1
            number == "0" -> countStones("1", iterations - 1)
            number.length % 2 == 0 ->
                countStones(number.half(), iterations - 1) +
                        countStones(number.secondHalf(), iterations - 1)
            else -> countStones((number.toLong() * 2024).toString(), iterations - 1)
        }.also { memo[key] = it }
    }

    fun part1() = input.sumOf { countStones(it, 25) }
    
    fun part2() = input.sumOf { countStones(it, 75) }

}
