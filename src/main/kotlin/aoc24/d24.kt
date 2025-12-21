package aoc24

import measured
import java.io.File
import kotlin.collections.contains

object Day24 {

    val input = File("inputs/aoc24/d24.txt").readLines()

    val initials = input.takeWhile { it.isNotEmpty() }.map {
        it.split(": ").let { it[0] to it[1].toInt() }
    }

    data class Gate(
        val a: String,
        val b: String,
        val op: String,
        var target: String
    )

    val gates = input.drop(initials.size + 1).map {
        it.split(" ").let { Gate(it[0], it[2], it[1], it[4]) }
    }

    fun compute(x: Long, y: Long) : Long {
        val ix = x.toString(2).padStart(45, '0').reversed()
        val iy = y.toString(2).padStart(45, '0').reversed()
        val initialsX = ix.mapIndexed { i, v -> "x${i.toString().padStart(2, '0')}" to v.digitToInt() }
        val initialsY = iy.mapIndexed { i, v -> "y${i.toString().padStart(2, '0')}" to v.digitToInt() }
        val initials = (initialsX + initialsY).toMap()

        val known = initials.toMap().toMutableMap()
        while (gates.any { it.target !in known }) {
            val c = gates
                .filter { it.target !in known && it.a in known && it.b in known }
            if (c.size == 0) return -1L
            c.forEach {
                    known[it.target] = when {
                        it.op == "AND" -> known[it.a]!! * known[it.b]!!
                        it.op == "OR" -> minOf(1, known[it.a]!! + known[it.b]!!)
                        else -> if (known[it.a]!! != known[it.b]!!) 1 else 0
                    }
                }
        }
        return known.filter { it.key.startsWith("z") }.toList().sortedBy { it.first }.map { it.second }.joinToString("").reversed().toLong(2)
    }

    fun part1(): Long {
        val known = initials.toMap().toMutableMap()
        while (gates.any { it.target !in known }) {
            gates
                .filter { it.target !in known && it.a in known && it.b in known }
                .forEach {
                    known[it.target] = when {
                        it.op == "AND" -> known[it.a]!! * known[it.b]!!
                        it.op == "OR" -> minOf(1, known[it.a]!! + known[it.b]!!)
                        else -> if (known[it.a]!! != known[it.b]!!) 1 else 0
                    }
                }
        }
        return known.filter { it.key.startsWith("z") }.toList().sortedBy { it.first }.map { it.second }.joinToString("").reversed().toLong(2)
    }

    fun swapGates(i: Int, j: Int) {
        val help = gates[i].target
        gates[i].target = gates[j].target
        gates[j].target = help
    }

    fun part2(): List<String> {
        val incorrectDigits = mutableListOf<Int>()
        for (y in 0..44) {
            val nx = 0L
            val ny = ("1" + "0".repeat(y)).toLong(2)
            val expects = nx + ny
            val gets = compute(nx, ny)
            if (expects != gets) { incorrectDigits += y }
        }

        val potentialSolutions = incorrectDigits.associateWith { mutableListOf<Pair<Int, Int>>() }
        for (digit in incorrectDigits) {
            val ny = ("1" + "0".repeat(digit)).toLong(2)
            for (i in 0..<gates.size - 1) {
                for (j in (i + 1)..<gates.size) {
                    val help = gates[i].target
                    gates[i].target = gates[j].target
                    gates[j].target = help
                    val gets = compute(0, ny)
                    if (gets == ny) {
                        potentialSolutions[digit]!! += i to j
                    }
                    gates[j].target = gates[i].target
                    gates[i].target = help
                }
            }
        }


        val values = potentialSolutions.values.toList()
        val results = mutableListOf<String>()
        for (i in values[0]){
            for (j in values[1]){
                for (k in values[2]){
                    for (l in values[3]){
                        var isCorrect = true
                        swapGates(i.first, i.second)
                        swapGates(j.first, j.second)
                        swapGates(k.first, k.second)
                        swapGates(l.first, l.second)
                        for (x in 0..44) {
                            for (y in 0..44) {
                                val nx = ("1" + "0".repeat(x)).toLong(2)
                                val ny = ("1" + "0".repeat(y)).toLong(2)
                                val expects = nx + ny
                                val gets = compute(nx, ny)
                                if (expects != gets) {
                                    isCorrect = false
                                    break
                                }
                            }
                            if (!isCorrect) break
                        }
                        if (isCorrect) {
                            val l = listOf(
                                gates[i.first].target,
                                gates[i.second].target,
                                gates[j.first].target,
                                gates[j.second].target,
                                gates[k.first].target,
                                gates[k.second].target,
                                gates[l.first].target,
                                gates[l.second].target)
                            results += l.sorted().joinToString(",")
                        }
                        swapGates(i.first, i.second)
                        swapGates(j.first, j.second)
                        swapGates(k.first, k.second)
                        swapGates(l.first, l.second)
                    }
                }
            }
        }
        return results
    }

}

fun main() {
    measured { Day24.part1() }
    measured { Day24.part2() }
}
