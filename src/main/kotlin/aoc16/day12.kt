package aoc16

import measuredNano
import java.io.File
import aoc16.Day12.Instruction.*

object Day12 {

    sealed interface Instruction {
        class CpyReg(val from: Int, val to: Int) : Instruction
        class CpyNum(val from: Int, val to: Int) : Instruction
        class Inc(val r: Int, val by: Int) : Instruction
        class JnzReg(val r: Int, val by: Int) : Instruction
        class JnzNum(val num: Int, val by: Int) : Instruction
    }

    val instructions = File("inputs/aoc16/day12.txt").readLines()
        .map {
            val split = it.split(" ")
            when (split[0]) {
                "cpy" -> if (split[1].toIntOrNull() == null) CpyReg(split[1][0] - 'a', split[2][0] - 'a') else CpyNum(split[1].toInt(), split[2][0] - 'a')
                "inc" -> Inc(split[1][0] - 'a', 1)
                "dec" -> Inc(split[1][0] - 'a', -1)
                "jnz" -> if (split[1].toIntOrNull() == null) JnzReg(split[1][0] - 'a', split[2].toInt()) else JnzNum(split[1].toInt(), split[2].toInt())
                else -> throw IllegalArgumentException("Unknown instruction type: $split")
            }
        }

    fun solve(r: IntArray): Int {
        var ip = 0
        while (ip < instructions.size) {
            when (val i = instructions[ip]) {
                is CpyReg -> { r[i.to] = r[i.from]; ip++ }
                is CpyNum -> { r[i.to] = i.from; ip++ }
                is Inc -> { r[i.r] += i.by; ip++ }
                is JnzReg -> ip += if (r[i.r] != 0) i.by else 1
                is JnzNum -> ip += if (i.num != 0) i.by else 1
            }
        }
        return r[0]
    }

    fun part1() = solve(intArrayOf(0, 0, 0, 0))

    fun part2() = solve(intArrayOf(0, 0, 1, 0))
}

fun main() {
    measuredNano { Day12.part1() }
    measuredNano { Day12.part2() }
}
