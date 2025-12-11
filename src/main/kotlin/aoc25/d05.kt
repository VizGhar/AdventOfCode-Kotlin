package aoc25

import measured
import java.io.File

object Day5 {

    fun part1(): String {
        val inp = File("inputs/aoc25/d5.txt").readLines()
        val ranges = inp.takeWhile { it.isNotEmpty() }.map { it.split("-").let { it[0].toLong()..it[1].toLong() } }
        return inp.drop(ranges.size + 1).count { n -> ranges.any { it.contains(n.toLong()) } }.toString()
    }

    fun part2(): String {
        val inp = File("inputs/aoc25/d5.txt").readLines()
        val ranges = inp.takeWhile { it.isNotEmpty() }.map { it.split("-").let { it[0].toLong()..it[1].toLong() } }
            .sortedBy { it.first }
            .toMutableList()

        var i = 0
        do {
            while (ranges.size > i + 1 && ranges[i + 1].first in ranges[i]) {
                ranges[i] = ranges[i].first..maxOf(ranges[i + 1].last, ranges[i].last)
                ranges.removeAt(i + 1)
            }
        } while (++i < ranges.size)
        return ranges.sumOf { it.last - it.first + 1 }.toString()
    }
}

fun main() {
    measured { Day5.part1() }
    measured { Day5.part2() }
}