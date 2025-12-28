package aoc15

import measuredNano
import java.io.File
import kotlin.math.sqrt

object Day20 {

    val input = File("inputs/aoc15/d20.txt").readText().toInt()

    fun primeFactors(n: Long): List<Long> {
        val factors = mutableListOf(1L)
        var number = n
        for (i in 2L..sqrt(number.toDouble()).toLong()) {
            while (number % i == 0L) {
                factors.add(i)
                number /= i
            }
        }
        return factors
    }

    fun <T> combinations(list: List<T>): List<List<T>> {
        val result = mutableListOf<List<T>>()

        fun backtrack(start: Int, current: MutableList<T>) {
            result.add(current.toList())
            for (i in start until list.size) {
                current.add(list[i])
                backtrack(i + 1, current)
                current.removeAt(current.size - 1)
            }
        }

        backtrack(0, mutableListOf())
        return result
    }

    fun countGiftsAt(
        id: Int,
        presentsPerHouse: Int,
        maxHouseVisited: Int
    ): Long {
        val factors = primeFactors(id.toLong())
        val combinations = combinations(factors).filter { it.isNotEmpty() }.distinctBy { it.reduce { a, b -> a * b } }
        val viableCombinations = combinations
            .filter {
                val res = factors.toMutableList()
                for (v in it) {
                    res.removeAt(res.indexOfFirst { it == v })
                }
                runCatching { res.reduce { a, b -> a * b }}.getOrElse { 0 } <= maxHouseVisited
            }
            .map { it.reduce { a, b -> a * b } }
            .distinct()

        return viableCombinations.sum() * presentsPerHouse
    }

    fun part1() = (1..input).first { countGiftsAt(it, 10, Int.MAX_VALUE) >= 33100000L }

    fun part2() = (1..input).first { countGiftsAt(it, 11, 50) >= input }

}

fun main() {
    measuredNano { Day20.part1() }
    measuredNano { Day20.part2() }
}

