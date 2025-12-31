package aoc16

import measuredNano
import java.io.File
import kotlin.text.indices

object Day8 {

    sealed interface Instruction {
        fun act(map: Array<CharArray>)

        class On(val w: Int, val h: Int): Instruction {
            override fun act(map: Array<CharArray>) {
                for (y in 0..<h) {
                    for (x in 0..<w) {
                        map[y][x] = '#'
                    }
                }
            }
        }

        class RotateRow(val y: Int, val d: Int) : Instruction {
            override fun act(map: Array<CharArray>) {
                val replacement = map[y].takeLast(d) + map[y].dropLast(d)
                for (x in replacement.indices) {
                    map[y][x] = replacement[x]
                }
            }
        }

        class RotateCol(val x: Int, val d: Int) : Instruction {
            override fun act(map: Array<CharArray>) {
                val original = map.map{ it[x] }
                val replacement = original.takeLast(d) + original.dropLast(d)
                for (y in replacement.indices) {
                    map[y][x] = replacement[y]
                }
            }
        }
    }

    val input = File("inputs/aoc16/day8.txt")
        .readLines()

    val map = Array(6) { CharArray(50) { '.' } }

    fun part1(): Int {
        val instructions = input.map {
            when {
                it.startsWith("rect") -> Instruction.On(it.substringAfter(" ").substringBefore("x").toInt(), it.substringAfter("x").toInt())
                it.startsWith("rotate row") -> Instruction.RotateRow(it.substringAfter("y=").substringBefore(" ").toInt(), it.substringAfter(" by ").toInt())
                it.startsWith("rotate column") -> Instruction.RotateCol(it.substringAfter("x=").substringBefore(" ").toInt(), it.substringAfter(" by ").toInt())
                else -> throw IllegalStateException()
            }
        }
        for (instruction in instructions) {
            instruction.act(map)
        }
        return map.sumOf { it.count { it == '#' } }
    }

    fun part2() = map.joinToString("\n") {it.joinToString("")}

}
fun main() {
    measuredNano { Day8.part1() }
    measuredNano { Day8.part2() }
}