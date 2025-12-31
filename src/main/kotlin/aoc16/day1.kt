package aoc16

import measuredNano
import java.io.File
import kotlin.math.abs

object Day1 {

    val input = File("inputs/aoc16/day1.txt").readLines()[0].split(", ")

    val directionMap = mutableMapOf(
        ((0 to -1) to 'R') to (1 to 0),
        ((0 to -1) to 'L') to (-1 to 0),
        ((0 to 1) to 'R') to (-1 to 0),
        ((0 to 1) to 'L') to (1 to 0),
        ((-1 to 0) to 'R') to (0 to -1),
        ((-1 to 0) to 'L') to (0 to 1),
        ((1 to 0) to 'R') to (0 to 1),
        ((1 to 0) to 'L') to (0 to -1)
    )

    var finalPosition = 0 to 0
    var repeatedPosition = 0 to 0

    fun path() {
        if (finalPosition != 0 to 0) return
        var direction = (0 to -1)
        var position = 0 to 0
        val positions = mutableSetOf(position.copy())
        for (move in input) {
            direction = directionMap[direction to move[0]]!!
            repeat (move.substring(1).toInt()) {
                val newPosition = position.first + direction.first to position.second + direction.second
                if (newPosition in positions && repeatedPosition == 0 to 0) {
                    repeatedPosition = newPosition
                }
                positions += newPosition
                position = newPosition
            }
        }
        finalPosition = position
    }

    fun part1() = path()
        .let { finalPosition }
        .let { abs(it.first) + abs(it.second) }

    fun part2() = path()
        .let { repeatedPosition }
        .let { abs(it.first) + abs(it.second) }

}

fun main() {
    measuredNano { Day1.part1() }
    measuredNano { Day1.part2() }
}