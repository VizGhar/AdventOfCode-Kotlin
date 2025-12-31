package aoc16

import measuredNano
import java.io.File
import java.security.MessageDigest

object Day17 {

    private val md = MessageDigest.getInstance("MD5")

    val input = File("inputs/aoc16/day17.txt").readText()

    private fun String.md5(): String { md.reset(); return md.digest(this.toByteArray()).toHexString().take(4) }
    private fun String.toOpenings() = md5().take(4).map { if (it in 'b'..'f') true else false }

    private data class Node(val x: Int, val y: Int, val path: String)
    private var directions = listOf(0 to -1, 0 to 1, -1 to 0, 1 to 0)
    private var directionsT = listOf("U", "D", "L", "R")

    private var part1 = ""
    private var part2 = 0

    fun solve() {
        val stack = ArrayDeque<Node>()
        stack.add(Node(0, 0, ""))
        while (stack.isNotEmpty()) {
            val (x, y, path) = stack.removeFirst()
            if (x == 3 && y == 3) {
                if (part1.isEmpty()) part1 = path
                if (path.length > part2) part2 = path.length
                continue
            }
            val openings = "$input$path".toOpenings()
            for (i in openings.indices) {
                if (openings[i] && x + directions[i].first in 0..3 && y + directions[i].second in 0..3)
                    stack.add(Node(x + directions[i].first, y + directions[i].second, path + directionsT[i]))
            }
        }
    }

    fun part1(): String {
        if (part1.isEmpty()) solve()
        return part1
    }

    fun part2(): Int {
        if (part2 == 0) solve()
        return part2
    }

}


fun main() {
    measuredNano { Day17.part1() }
    measuredNano { Day17.part2() }
}