package aoc15

import measuredNano
import java.io.File

object Day6 {

    data class Entry(
        val x1: Int,
        val y1: Int,
        val x2: Int,
        val y2: Int,
        val action: (Boolean) -> Boolean,
        val action2: (Int) -> Int,
    )

    val input =  File("inputs/aoc15/d06.txt").readLines()
        .map {
            Entry(
                x1 = it.substringBefore(",").substringAfterLast(" ").toInt(),
                y1 = it.substringAfter(",").substringBefore(" ").toInt(),
                x2 = it.substringBeforeLast(",").substringAfterLast(" ").toInt(),
                y2 = it.substringAfterLast(",").toInt(),
                action = if (it.startsWith("turn on")) { { true } } else if (it.startsWith("turn off")) { { false } } else { { !it } },
                action2 = if (it.startsWith("turn on")) { { it + 1 } } else if (it.startsWith("turn off")) { { maxOf(0, it - 1) } } else { { it + 2 } },
            )
        }

    fun part1(): Int {
        val map = Array(1000) { Array(1000) { false } }
        input.forEach {
            for (x in it.x1..it.x2) {
                for (y in it.y1..it.y2) {
                    map[y][x] = it.action(map[y][x])
                }
            }
        }
        return map.sumOf { it.count { it } }
    }

    fun part2(): Int {
        val map = Array(1000) { Array(1000) { 0 } }
        input.forEach {
            for (x in it.x1..it.x2) {
                for (y in it.y1..it.y2) {
                    map[y][x] = it.action2(map[y][x])
                }
            }
        }
        return map.sumOf { it.sum() }
    }

}

fun main() {
    measuredNano { Day6.part1() }
    measuredNano { Day6.part2() }
}