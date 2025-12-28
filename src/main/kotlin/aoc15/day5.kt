package aoc15

import measuredNano
import java.io.File

object Day5 {

    val input =  File("inputs/aoc15/d05.txt").readLines()

    val String.isNice: Boolean get() =
        count { it in "aeiou" } >= 3 &&
        windowed(2).any { it[0] == it[1] } &&
        listOf("ab", "cd", "pq", "xy").none { it in this }


    fun part1() = input.count { it.isNice }
    fun part2() = input.count { text ->
        val variants = text.windowed(2)
        val picked = variants.distinct().filter { a -> variants.count { it == a } > 1 }
        val a = picked.any { pick ->
            Regex(Regex.escape(pick))
                .findAll(text)
                .map { it.range.first }
                .toList()
                .windowed(2)
                .any { it[0] + 1 < it[1] }
        }

        a && text.windowed(3).any { it[0] == it[2] }
    }

}

fun main() {
    measuredNano { Day5.part1() }
    measuredNano { Day5.part2() }
}