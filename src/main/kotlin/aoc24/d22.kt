package aoc24

import measured
import java.io.File

object Day22 {

    private val inputs = File("inputs/aoc24/d22.txt").readLines().map { it.toLong() }

    fun part1() : Long {
        return inputs.sumOf { number ->
            var number = number
            repeat(2000) {
                val secret1 = ((number * 64) xor number) % 16777216L
                val secret2 = ((secret1 / 32) xor secret1) % 16777216L
                val secret3 = ((secret2 * 2048) xor secret2) % 16777216L
                number = secret3
            }
            number
        }
    }

    fun getVariants(current: List<Int> = emptyList()): List<List<Int>> {
        if (current.size == 4) return listOf(current)
        val sum = current.sum()
        val min = if (current.size == 3) 0 else maxOf(-9, -sum - 9)
        return (min..minOf(9, -sum + 9)).map { num ->
            getVariants(current + num)
        }.flatten()
    }

    fun part2() : Long {
        val vars = getVariants()
        var id = 0
        val result = vars.maxOf { variant ->
            if (++id % 100 == 0) { println("${id}/${vars.size}") }
            val currentChange = mutableListOf<Int>()
            inputs.sumOf { number ->
                var number = number
                repeat(2000) {
                    val secret1 = ((number * 64) xor number) % 16777216L
                    val secret2 = ((secret1 / 32) xor secret1) % 16777216L
                    val secret3 = ((secret2 * 2048) xor secret2) % 16777216L
                    val change = secret3 % 10 - number % 10
                    currentChange += change.toInt()
                    if (currentChange.size > 4) currentChange.removeFirst()
                    if (currentChange == variant) {
                        return@sumOf secret3 % 10
                    }
                    number = secret3
                }
                0
            }
        }
        return  result
    }
}

fun main() {
    measured { Day22.part1() }
    measured { Day22.part2() }
}
