package aoc16

import measuredNano
import java.io.File
import java.security.MessageDigest

object Day18 {

    val input = File("inputs/aoc16/day18.txt").readText().map { it == '^' }.toBooleanArray()

    val mapping = booleanArrayOf(false, true, false, true, true, false, true, false)

    fun BooleanArray.isTrap(x: Int) = if (x in 0..<size && this[x]) 1 else 0
    fun BooleanArray.resolve(x: Int) = mapping[(isTrap(x - 1) * 4 + isTrap(x) * 2 + isTrap(x + 1))]

    var part1 = 0
    var part2 = 0

    fun solve() {
        val row = input
        var counter = row.count { !it }
        val next = BooleanArray(row.size)
        repeat(399999) {
            if (it == 39) part1 = counter
            for (i in row.indices) { next[i] = row.resolve(i) }
            for (i in row.indices) { row[i] = next[i]; if (!row[i]) counter++ }
        }
        part2 = counter
    }

    fun part1(): Int {
        if (part1 == 0) solve()
        return part1
    }

    fun part2(): Int {
        if (part2 == 0) solve()
        return part2
    }
}

fun main() {
    measuredNano { Day18.part1() }
    measuredNano { Day18.part2() }
}