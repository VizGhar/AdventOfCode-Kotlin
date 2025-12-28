package aoc15

import measuredNano
import java.io.File

object Day7 {

    data class Node(var a: String, var b: String, val op: String, val out: String)

    val input = File("inputs/aoc15/d07.txt")
        .readLines()
        .map { line ->
            val (inn, out) = line.split(" -> ")
            val inns = inn.split(" ")
            when {
                inns.size == 1 -> Node(inns[0], inns[0], "-", out)
                inns[0] == "NOT" -> Node(inns[1], inns[1], "NOT", out)
                else -> Node(inns[0], inns[2], inns[1], out)
            }
        }

    val memo = mutableMapOf<String, Int>()
    fun solve(node: Node): Int {
        return memo.getOrPut(node.out) {
            val valA = node.a.toIntOrNull() ?: solve(input.first { it.out == node.a })
            val valB = if (node.a == node.b) valA else node.b.toIntOrNull() ?: solve(input.first { it.out == node.b })

            when (node.op) {
                "AND" -> valA and valB
                "OR" -> valA or valB
                "NOT" -> valA.inv()
                "LSHIFT" -> valA shl valB
                "RSHIFT" -> valA ushr valB
                else -> valA
            } and 65535
        }
    }

    var newA = -1

    fun part1(): Int = solve(input.first { it.out == "a" }).also { newA = it }

    fun part2(): Int {
        if (newA == -1) part1()
        memo.clear()
        input.first { it.out == "b" }.let { it.a = newA.toString(); it.b = newA.toString() }
        return solve(input.first { it.out == "a" })
    }

}

fun main() {
    measuredNano { Day7.part1() }
    measuredNano { Day7.part2() }
}