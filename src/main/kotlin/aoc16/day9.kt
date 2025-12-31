package aoc16

import measuredNano
import java.io.File
import kotlin.text.indices

object Day9 {

    val input = File("inputs/aoc16/day9.txt").readText()

    fun part1() {
        var index = 0
        var result = 0
        while (true) {
            val openBracketIndex = input.indexOf("(", index)
            if (openBracketIndex == -1) break
            result += openBracketIndex - index
            val closeBracketIndex = input.indexOf(")", index)
            val s = input.substring(openBracketIndex + 1, closeBracketIndex)
            val count = s.substringBefore("x").toInt()
            val times = s.substringAfter("x").toInt()
            index = closeBracketIndex + 1 + count
            result += count * times
        }
        result += input.length - index
        println(result)
    }

    fun decompress(substr: String): Long {
        var index = 0
        var result = 0L
        while (true) {
            val openBracketIndex = substr.indexOf("(", index)
            if (openBracketIndex == -1) break
            result += openBracketIndex - index
            val closeBracketIndex = substr.indexOf(")", index)
            val s = substr.substring(openBracketIndex + 1, closeBracketIndex)
            val count = s.substringBefore("x").toInt()
            val times = s.substringAfter("x").toInt()
            index = closeBracketIndex + 1 + count
            result += times * decompress(substr.substring(closeBracketIndex + 1, closeBracketIndex + 1 + count))
        }
        result += substr.length - index
        return result
    }

    fun part2() = decompress(input)
}
fun main() {
    measuredNano { Day9.part1() }
    measuredNano { Day9.part2() }
}