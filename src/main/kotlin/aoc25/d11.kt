package aoc25

import measured
import java.io.File

object Day11 {

    data class Node(val name: String, val targets: List<String>)

    val paths = (File("inputs/aoc25/d11.txt").readLines() + "out: ?").map { line ->
        Node(line.substringBefore(": "), line.substringAfter(": ").split(" "))
    } + Node("out", emptyList())

    val memo = mutableMapOf<String, Long>()

    fun find(from: String, to: String): Long {
        memo.clear()
        return rec(paths.filter { it.name == from }, to)
    }

    fun rec(path: List<Node>, target: String): Long {
        val current = path.last()
        if (current.name == target) return 1L
        if (current.name in memo) return memo[current.name]!!
        return current.targets.filter { it != "?" }.sumOf { tar ->
            rec(path + (paths.firstOrNull { it.name == tar } ?: throw IllegalStateException(tar)), target)
        }.also { memo[current.name] = it }
    }

    fun part1() = find("you", "out")

    fun part2() = find("svr", "fft") * find("fft", "dac") * find("dac", "out")
}

fun main() {
    measured { Day11.part1() }
    measured { Day11.part2() }
}