package aoc16

import measuredNano
import java.io.File

object Day16 {

    val input = File("inputs/aoc16/day16.txt").readText()

    private fun String.dragon() = "${this}0${reversed().map { if (it == '0') '1' else '0' }.joinToString("")}"

    fun part1(): String {
        var text = input
        while (text.length < 272) {
            text = text.dragon()
        }
        text = text.take(272)
        while (text.length %2 != 1) {
            text = text.chunked(2).map { if (it[0] == it[1]) '1' else '0' }.joinToString("")
        }
        return text
    }

    fun part2(): String {
        var text = input
        var i = 0
        while (text.length < 35651584) {
            text = text.dragon()
            i++
        }
        text = text.take(35651584)
        while (text.length %2 != 1) {
            text = text.chunked(2).map { if (it[0] == it[1]) '1' else '0' }.joinToString("")
        }
        return text
    }

}


fun main() {
    measuredNano { Day16.part1() }
    measuredNano { Day16.part2() }
}