package aoc25

import measured
import java.io.File

object Day2 {

    val input = File("inputs/aoc25/d2.txt").readText().split(",").map { it.split("-").let { it[0].toLong()..it[1].toLong() } }

    fun part1Slow() = input.sumOf { range ->
        range.sumOf {
            val str = it.toString()
            if (str.length % 2 == 0 && str.take(str.length / 2) == str.substring(str.length / 2)) it else 0
        }
    }

    fun part2Slow() = input.sumOf { range ->
        range.sumOf { num ->
            val str = num.toString()
            (1..str.length / 2).mapNotNull { partLen ->
                if (str.length % partLen != 0) 0L
                else if (str.chunked(partLen).distinct().size == 1) num
                else 0L
            }.distinct().sum()
        }
    }

    fun part1() = input.sumOf { range ->
        // faster solution
        val minStringLength = range.first.toString().length
        val maxStringLength = range.last.toString().length
        val nums = mutableSetOf<Long>()
        for (l in minStringLength..maxStringLength) {
            if (l % 2 != 0) continue
            val pl = l / 2
            val from =
                if (minStringLength != l) "1${"0".repeat(pl-1)}".toLong()
                else range.first.toString().take(pl).toLong()
            val to = if (l == minStringLength)
                "9".repeat(from.toString().length).toLong() else
                range.last.toString().takeLast(pl).toLong()
            for (num in from..to) {
                val fullNum = num.toString().repeat(l / pl).toLong()
                if (fullNum in range) nums += fullNum
            }
        }
        nums.sum()
    }

    fun part2() = input.sumOf { range ->
        val minStringLength = range.first.toString().length
        val maxStringLength = range.last.toString().length
        val nums = mutableSetOf<Long>()
        for (l in minStringLength..maxStringLength) {
            for (pl in 1..l/2) {
                if (l % pl != 0) continue
                val from =
                    if (minStringLength != l) "1${"0".repeat(pl-1)}".toLong()
                    else range.first.toString().take(pl).toLong()
                val to = if (l == minStringLength)
                    "9".repeat(from.toString().length).toLong() else
                    range.last.toString().takeLast(pl).toLong()
                for (num in from..to) {
                    val fullNum = num.toString().repeat(l / pl).toLong()
                    if (fullNum in range) nums += fullNum
                }
            }
        }
        nums.sum()
    }
}

fun main() {
    measured { Day2.part1Slow() }
    measured { Day2.part1() }
    measured { Day2.part2Slow() }
    measured { Day2.part2() }
}
