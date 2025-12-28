package aoc15

import measuredNano
import java.io.File

object Day1 {

    val input = File("inputs/aoc15/d01.txt").readText()

    fun part1() = input.fold(0) { a, b -> a + if (b == '(') 1 else -1 }

    fun part2() : Int {
        var pos = 1
        var floor = 0
        for (c in input) {
            floor += if (c == '(') 1 else -1
            if (floor == -1) return pos
            pos++
        }
        return -1
    }

}

fun main() {
    measuredNano { Day1.part1() }
    measuredNano { Day1.part2() }
}