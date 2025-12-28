package aoc15

import measuredNano
import java.io.File

object Day25 {

    val input = File("inputs/aoc15/d25.txt").readText().let {
        it.substringAfter("column ").substringBefore(".").toInt() to it.substringAfter("row ").substringBefore(",").toInt()
    }

    fun sum(from: Int, to: Int) : Int {
        val m = to * (to + 1) / 2
        val n = from * (from - 1) / 2
        return m - n
    }

    fun part1(): Long {
        val xBase = sum(1, input.first)
        val id = xBase + sum(input.first, input.first + input.second - 2)
        var num = 20151125L
        repeat(id - 1) { num = (num * 252533) % 33554393 }
        return num
    }
}

fun main() {
    measuredNano { Day25.part1() }
}
