package aoc16

import measuredNano
import java.io.File

object Day13 {

    val input = File("inputs/aoc16/day13.txt").readText().toInt()

    fun key(x: Int, y: Int) = (x shl 16) xor y

    fun isWall(x: Int, y: Int) = x < 0 || y < 0 || Integer.bitCount(x * x + 3 * x + 2 * x * y + y + y * y + input) and 1 == 1

    var part1 = 0
    var part2 = 0

    fun solve() {
        val dx = intArrayOf(-1, 0, 1, 0)
        val dy = intArrayOf(0, -1, 0, 1)

        val queue = ArrayDeque<IntArray>()
        val visited = HashSet<Int>()

        queue.add(intArrayOf(1, 1, 0))
        visited += key(1, 1)

        while (queue.isNotEmpty()) {
            val (x, y, depth) = queue.removeFirst()
            if (x == 31 && y == 39) { part1 = depth; return }
            if (depth <= 50) part2++
            for (i in 0..3) {
                val nx = x + dx[i]
                val ny = y + dy[i]
                if (!isWall(nx, ny)) {
                    if (visited.add(key(nx, ny))) queue.add(intArrayOf(nx, ny, depth + 1))
                }
            }
        }
    }

    fun part1(): Int {
        if (part1 == 0) { solve() }
        return part1
    }

    fun part2(): Int {
        if (part2 == 0) { solve() }
        return part2
    }
}

fun main() {
    measuredNano { Day13.part1() }
    measuredNano { Day13.part2() }
}