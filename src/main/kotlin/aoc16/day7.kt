package aoc16

import measuredNano
import java.io.File
import kotlin.text.indices

object Day7 {

    val regex = "\\[([^\\[]*)]".toRegex()

    val input = File("inputs/aoc16/day7.txt")
        .readLines()
        .map { line ->
            val outs = regex.findAll(line).map { it.groupValues[1] }.toList()
            val ins = line.split(regex)
            outs to ins
        }

    fun String.isAbba() = (0..length-4).any { substring(it, it + 4).let { it[0] == it[3] && it[1] == it[2] && it[0] != it[1] } }

    fun String.getABAs() = windowed(3).filter {
        val (a, b, c) = it.toCharArray()
        a == c && a != b
    }

    fun part1() = input.count { (outs, ins) ->
        outs.none { it.isAbba() } && ins.any { it.isAbba() }
    }

    fun part2() = input.count { (outs, ins) ->
        outs.any {
            it.getABAs().any { out ->
                ins.any {
                    it.getABAs().any {
                        it[0] == out[1] && it[1] == out[0]
                    }
                }
            }
        }
    }
}

fun main() {
    measuredNano { Day7.part1() }
    measuredNano { Day7.part2() }
}