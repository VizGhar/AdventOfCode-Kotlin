package aoc15

import measuredNano
import java.io.File

object Day3 {

    val directions = mapOf(
        '<' to (-1 to 0),
        '^' to (0 to -1),
        '>' to (1 to 0),
        'v' to (0 to 1)
    )

    val input =  File("inputs/aoc15/d03.txt").readText().map { directions[it]!! }

    fun part1(): Int {
        var position = 0 to 0
        val visits = mutableSetOf(0 to 0)
        input.forEach {
            position = position.first + it.first to position.second + it.second
            visits += position
        }
        return visits.size
    }

    fun part2(): Int {
        var positionA = 0 to 0
        var positionB = 0 to 0
        val visits = mutableSetOf(0 to 0)
        for (i in input.indices) {
            val it = input[i]
            if (i % 2 == 0) {
                positionA = positionA.first + it.first to positionA.second + it.second
                visits += positionA
            } else {
                positionB = positionB.first + it.first to positionB.second + it.second
                visits += positionB
            }
        }

        return visits.size
    }
}

fun main() {
    measuredNano { Day3.part1() }
    measuredNano { Day3.part2() }
}