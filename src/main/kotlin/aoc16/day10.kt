package aoc16

import measuredNano
import java.io.File
import kotlin.collections.get
import kotlin.compareTo
import kotlin.text.toLong
import kotlin.times

object Day10 {

    val input = File("inputs/aoc16/day10.txt").readLines()

    data class Triple(val a: Int, val b: Int, val c: Int)

    var solution1: Int = -1
    var solution2: Int = -1

    fun solve() {
        val map = mutableMapOf<Int, List<Int>>()

        input.filter { it.startsWith("value ") }.forEach{
            val botId = it.substringAfter(" bot ").toInt()
            val value = it.substringBefore(" goes ").substringAfter("value ").toInt()
            map[botId] = (map[botId] ?: emptyList()) + value
        }

        val transforms = input.filter { it.startsWith("bot") }
            .map {
                val botId = it.substringAfter("bot ").substringBefore(" ").toInt()
                val lowToOutput = it.substringAfter("low to output ").substringBefore(" ").toIntOrNull()
                val lowToBot = it.substringAfter("low to bot ").substringBefore(" ").toIntOrNull()
                val highToOutput = it.substringAfter("high to output ").substringBefore(" ").toIntOrNull()
                val highToBot = it.substringAfter("high to bot ").substringBefore(" ").toIntOrNull()
                val low = lowToBot ?: lowToOutput?.let { -100 - it } ?: throw IllegalStateException()
                val high = highToBot ?: highToOutput?.let { -100 - it } ?: throw IllegalStateException()
                Triple(botId, low, high)
            }

        while (map.any { it.key >= 0 && it.value.size > 1 }) {
            val forward = map.filter { it.key >= 0 && it.value.size > 1 }
            for ((botId, value) in forward) {
                if (value.size == 2) {
                    if (value.sorted() == listOf(17, 61)) solution1 = botId
                    val (_, low, high) = transforms.first { it.a == botId }
                    map[low] = (map[low] ?: emptyList()) + value.min()
                    map[high] = (map[high] ?: emptyList()) + value.max()
                    map[botId] = emptyList()
                }
            }
        }
        solution2 = map.filter { it.key in listOf(-100, -101, -102) }.map{it.value[0]}.reduce { a, b -> a * b }
    }

    fun part1(): Int {
        if (solution1 == -1) solve()
        return solution1
    }

    fun part2(): Int {
        if (solution2 == -1) solve()
        return solution2
    }
}
fun main() {
    measuredNano { Day10.part1() }
    measuredNano { Day10.part2() }
}