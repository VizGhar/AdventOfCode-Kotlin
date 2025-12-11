package aoc25

import measured
import java.io.File

object Day6 {

    fun part1(): Long {
        val input = File("inputs/aoc25/d6.txt").readLines()
        val lines = input.map { it.split(" ").filter { it.isNotEmpty() } }
        return (0..<lines[0].size).sumOf { c ->
            val column = lines.map { it[c] }
            val operands = column.dropLast(1).map { it.toLong() }
            val operator = column.last()
            operands.reduce { a, b -> if (operator == "+") a + b else a * b }
        }
    }

    fun part2(): Long {
        val input = File("inputs/aoc25/d6.txt").readLines()
        val w = input[0].length
        val h = input.size

        val numList = mutableListOf<Long>()
        var result = 0L
        for (x in w - 1 downTo 0) {
            var col = ""
            for (y in 0..<h) {
                col += input[y][x]
            }
            col = col.trim()
            if (col.isEmpty()) continue
            val symbol = col.last().takeIf { it in listOf('+', '*') }
            val num = col.takeWhile { it.isDigit() }.toLong()
            numList += num
            if (symbol != null) {
                result += numList.reduce { a, b -> if (symbol == '+') a + b else a * b }
                numList.clear()
            }
        }
        return result
    }
}

fun main() {
    measured { Day6.part1() }
    measured { Day6.part2() }
}