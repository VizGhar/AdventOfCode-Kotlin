package aoc25

import measured
import java.io.File
import kotlin.math.sqrt

object Day8 {
    data class Box(val x: Long, val y: Long, val z: Long)
    data class Connection(val a: Box, val b: Box)

    fun part1(): Int {
        val input = File("inputs/aoc25/d8.txt").readLines()
            .map { line -> line.split(",").map { it.toLong() }.let { Box(it[0], it[1], it[2]) } }

        val connections = mutableListOf<Connection>()
        for (j in 0..<input.size - 1) {
            for (k in j + 1..<input.size) {
                connections += Connection(input[j], input[k])
            }
        }
        connections.sortBy { (a, b) ->
            val dx = (a.x - b.x).toDouble()
            val dy = (a.y - b.y).toDouble()
            val dz = (a.z - b.z).toDouble()
            sqrt(dx * dx + dy * dy + dz * dz)
        }
        val circuits = input.map { mutableSetOf(it) }.toMutableList()
        repeat(1000) {
            val (a, b) = connections[it]
            val circuitIdA = circuits.indexOfFirst { a in it }
            val circuitIdB = circuits.indexOfFirst { b in it }
            when {
                circuitIdB == -1 -> circuits[circuitIdA] += listOf(a, b)
                circuitIdA == -1 -> circuits[circuitIdB] += listOf(a, b)
            }
            if (circuitIdA != circuitIdB) {
                circuits[circuitIdA] += circuits.removeAt(circuitIdB)
            }
        }
        return (circuits.sortedByDescending { it.size }.take(3).map { it.size }.reduce { a, b -> a * b })
    }

    fun part2(): Long {
        val input = File("inputs/aoc25/d8.txt").readLines()
            .map { line -> line.split(",").map { it.toLong() }.let { Box(it[0], it[1], it[2]) } }

        val connections = mutableListOf<Connection>()
        for (j in 0..<input.size - 1) {
            for (k in j + 1..<input.size) {
                connections += Connection(input[j], input[k])
            }
        }
        connections.sortBy { (a, b) ->
            val dx = (a.x - b.x).toDouble()
            val dy = (a.y - b.y).toDouble()
            val dz = (a.z - b.z).toDouble()
            sqrt(dx * dx + dy * dy + dz * dz)
        }
        val circuits = input.map { mutableSetOf(it) }.toMutableList()
        for ((a, b) in connections) {
            val circuitIdA = circuits.indexOfFirst { a in it }
            val circuitIdB = circuits.indexOfFirst { b in it }
            when {
                circuitIdB == -1 -> circuits[circuitIdA] += listOf(a, b)
                circuitIdA == -1 -> circuits[circuitIdB] += listOf(a, b)
            }
            if (circuitIdA != circuitIdB) {
                circuits[circuitIdA] += circuits.removeAt(circuitIdB)
            }
            if (circuits.size == 1) {
                return (a.x * b.x)
            }
        }
        return 0L
    }
}

fun main() {
    measured { Day8.part1() }
    measured { Day8.part2() }
}