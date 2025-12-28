package aoc15

import measuredNano
import java.io.File

object Day23 {

    val inp = File("inputs/aoc15/d23.txt").readLines().map { it.replace(",", "").replace("+", "").split(" ") }

    fun solve(a: Int, b: Int): Int {
        var a = a
        var b = b
        var line = 0
        while(line in 0..<inp.size) {
            val current = inp[line]
            when(current[0]) {
                "hlf" -> a /= 2
                "tpl" -> a *= 3
                "inc" -> if (current[1] == "a") a++ else b++
                "jmp" -> line += current[1].toInt() - 1
                "jie" -> if (a % 2 == 0) line += current[2].toInt() - 1
                "jio" -> if (a == 1) line += current[2].toInt() - 1
            }
            line++
        }
        return b
    }

    fun part1() = solve(0, 0)

    fun part2() = solve(1, 0)
}

fun main() {
    measuredNano { Day23.part1() }
    measuredNano { Day23.part2() }
}
