package aoc25

import measured
import java.io.File

object Day7 {
    val map = File("inputs/aoc25/d7.txt").readLines()

    val memoi = mutableSetOf<Pair<Int, Int>>()

    fun part1(x: Int = map[0].indexOf('S'), y: Int = 0): Int {
        if (y == map.size) return 0
        if (x !in 0..<map[0].length) return 0
        if (x to y in memoi) return 0
        memoi += x to y
        if (map[y][x] == '^') {
            return 1 + part1(x - 1, y) + part1(x + 1, y)
        } else {
            return part1(x, y + 1)
        }
    }

    val memoii = mutableMapOf<Pair<Int, Int>, Long>()

    fun part2(
        x: Int = map[0].indexOf('S'),
        y: Int = 0
    ): Long {
        if (y == map.size) return 1
        if (x to y in memoii) return memoii[x to y]!!
        return if (map[y][x] == '^') {
            (part2(x - 1, y) + part2(x + 1, y)).also {
                memoii[x to y] = it
            }
        } else {
            part2(x, y + 1)
        }
    }
}

fun main() {
    measured { Day7.part1() }
    measured { Day7.part2() }
}
