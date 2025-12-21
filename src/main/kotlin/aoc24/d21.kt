package aoc24

import measured
import java.io.File

object Day21 {

    private val keyTranslations = Array(11) { Array(11) { emptyList<String>() } }
    private val robotTranslations = Array(5) { Array(5) { emptyList<String>() } }

    private fun generateKeyTranslations() {
        if (keyTranslations[0][0].isNotEmpty()) return
        val symbols = mapOf((0 to 0) to 7, (1 to 0) to 8, (2 to 0) to 9, (0 to 1) to 4, (1 to 1) to 5, (2 to 1) to 6, (0 to 2) to 1, (1 to 2) to 2, (2 to 2) to 3, (1 to 3) to 0, (2 to 3) to 10)
        for (ax in 0..2) {
            for (ay in 0..3) {
                for (bx in 0..2) {
                    for (by in 0..3) {
                        if (ax to ay == 0 to 3 || bx to by == 0 to 3) continue
                        val horizontal = if (ax < bx) ">".repeat(bx - ax) else "<".repeat(ax - bx)
                        val vertical = if (ay < by) "v".repeat(by - ay) else "^".repeat(ay - by)
                        val variants = listOfNotNull("$horizontal${vertical}A".takeIf { bx to ay != 0 to 3 }, "$vertical${horizontal}A".takeIf { ax to by != 0 to 3 }).distinct()
                        keyTranslations[symbols[ax to ay]!!][symbols[bx to by]!!] = variants
                    }
                }
            }
        }

        val robotSymbols = mapOf((0 to 1) to 0, (1 to 0) to 1, (2 to 1) to 2, (1 to 1) to 3, (2 to 0) to 4)
        for (ax in 0..2) {
            for (ay in 0..1) {
                for (bx in 0..2) {
                    for (by in 0..1) {
                        if (ax to ay == 0 to 0 || bx to by == 0 to 0) continue
                        val horizontal = if (ax < bx) ">".repeat(bx - ax) else "<".repeat(ax - bx)
                        val vertical = if (ay < by) "v".repeat(by - ay) else "^".repeat(ay - by)
                        val variants = listOfNotNull("$horizontal${vertical}A".takeIf { bx to ay != 0 to 0 }, "$vertical${horizontal}A".takeIf { ax to by != 0 to 0 }).distinct()
                        robotTranslations[robotSymbols[ax to ay]!!][robotSymbols[bx to by]!!] = variants
                    }
                }
            }
        }
    }

    private val inputs = File("inputs/aoc24/d21.txt").readLines()

    private val x = mapOf('<' to 0, '^' to 1, '>' to 2, 'v' to 3, 'A' to 4)

    private val memo = Array(30) { mutableMapOf<Pair<Char, Char>, Long>()}

    fun composeVariants(alternatives: List<List<String>>, current: String = "", depth: Int = 0): List<String> {
        if (depth == alternatives.size) return listOf(current)
        return alternatives[depth].map { alternative ->
            composeVariants(alternatives, current + alternative, depth + 1)
        }.flatten().distinct().sortedBy { it.length }.let { list ->
            list.filter { it.length == list[0].length }
        }
    }

    fun length(from: Char, to: Char, maxDepth: Int, depth: Int = 1): Long {
        return memo[depth].getOrPut(from to to) {
            val parts = robotTranslations[x[from]!!][x[to]!!]
            if (depth == maxDepth) return@getOrPut parts[0].length.toLong()
            parts.minOf { part ->
                "A$part".windowed(2).sumOf { length(it[0], it[1], maxDepth, depth + 1) }
            }
        }
    }

    fun solve(robots: Int): Long {
        generateKeyTranslations()
        return inputs.sumOf { target ->
            val currentVariants = composeVariants("A$target".windowed(2).map { keyTranslations[it[0].digitToIntOrNull() ?: 10][it[1].digitToIntOrNull() ?: 10] })
            val length = currentVariants.minOf { current ->
                for (i in memo.indices) { memo[i].clear() }
                "A$current".windowed(2).sumOf { length(it[0], it[1], robots) }
            }
            length * target.dropLast(1).toLong()
        }
    }

    fun part1() = solve(2)
    fun part2() = solve(25)
}

fun main() {
    measured { Day21.part1() }
    measured { Day21.part2() }
}
