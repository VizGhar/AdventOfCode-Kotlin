package aoc15

import measuredNano
import java.io.File

object Day19 {

    val input = File("inputs/aoc15/d19.txt").readLines()
    val replacements = input.takeWhile { it.isNotEmpty() }.map { it.split(" => ").let { it[0] to it[1] } }
    val text = input.last()

    fun part1(): Int {
        val results = mutableSetOf<String>()
        for (i in text.indices) {
            for (replacement in replacements) {
                if (text.substring(i).startsWith(replacement.first)) {
                    results += text.take(i) + replacement.second + text.drop(i + replacement.first.length)
                }
            }
        }
        return results.size
    }

    fun part2(): Int {
        val elements = text.count { it.isUpperCase() }
        val ys = text.count { it == 'Y' }
        val ars = "Ar".toRegex().findAll(text).count()
        return elements - (ys + ars) * 2 - 1
    }
}

fun main() {
    measuredNano { Day19.part1() }
    measuredNano { Day19.part2() }
}

