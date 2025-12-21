package aoc24

import java.io.File

object Day19 {

    private data class Input(
        val availablePatterns: List<String>,
        val required: List<String>
    )

    private val input = File("inputs/aoc24/d19.txt").readLines().let {
        Input(
            availablePatterns = it[0].split(", "),
            required = it.drop(2)
        )
    }

    private val memo = mutableMapOf<String, Long>()

    private fun countPossibilities(
        patterns: List<String>,
        remaining: String,
        used: List<String>
    ): Long {
        memo[remaining]?.let { return it }          // memoization
        if (remaining.isEmpty()) return 1           // stop condition
        return patterns.sumOf { pattern ->          // recursion
            if (remaining.startsWith(pattern)) {
                countPossibilities(
                    patterns,
                    remaining.drop(pattern.length),
                    used + pattern
                )
            } else 0
        }.also { memo[remaining] = it }
    }

    private val possibilities by lazy {
        input.required.map {
            memo.clear()
            countPossibilities(input.availablePatterns, it, emptyList())
        }
    }

    fun part1() = possibilities.count { it > 0 }

    fun part2() = possibilities.sumOf { it }
}
