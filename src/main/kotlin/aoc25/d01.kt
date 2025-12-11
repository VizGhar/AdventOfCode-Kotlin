package aoc25

import measured
import java.io.File
import kotlin.math.abs

object Day1 {

    val input = File("inputs/aoc25/d1.txt").readLines()

    fun part1(): Int {
        var position = 50L
        return input.count { line ->
            position += line.drop(1).toInt() * if (line[0] == 'R') 1 else -1
            position % 100 == 0L
        }
    }

    fun part2(): Int {
        var position = 1000050
        return input.sumOf { line ->
            val newPosition = position + line.drop(1).toInt() * if (line[0] == 'R') 1 else -1
            val rotations = abs(position / 100 - newPosition / 100)
            when {
                position % 100 == 0 && newPosition < position -> rotations - 1
                newPosition % 100 == 0 && newPosition < position -> rotations + 1
                else -> rotations
            }.also { position = newPosition }
        }
    }
}

fun main() {
    measured { Day1.part1() }
    measured { Day1.part2() }
}
