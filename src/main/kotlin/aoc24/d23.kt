package aoc24

import measured
import java.io.File
import kotlin.collections.plusAssign

object Day23 {

    val input = File("inputs/aoc24/d23.txt").readLines()
        .map { it.split("-").let { listOf(it[0] to it[1], it[1] to it[0]) } }
        .flatten()
        .groupBy { it.first }

    val uniqueNames = input.keys

    fun part1(): Int {
        val finds = mutableSetOf<List<String>>()
        for ((node, connections) in input) {
            val targets = connections.map { it.second }.distinct()
            // node is connected to each of targets
            // is any of targets connected to other target?
            for (target in targets) {
                val remainingTargets = targets - target
                input[target]!!.filter { it.second in remainingTargets}.forEach { other ->
                    finds += listOf(node, target, other.second).sorted()
                }
            }
        }

        val libr = finds.filter { it.any { it.startsWith('t') } }
        return libr.size
    }

    val memo = mutableMapOf<List<String>, Int>()
    var record = 0
    var bestSubgraph = listOf<String>()

    fun biggestFullSubgraph(selected: List<String>) : Int {
        memo[selected]?.let { return it }

        val allNewConnections = selected.map { name ->
            input[name]
                ?.map { it.second }
                ?.filter { it !in selected }
                ?: throw IllegalStateException()
        }

        val neighbors = allNewConnections.flatten().distinct()

        val candidates = neighbors.filter { candidate ->
            allNewConnections.all { it.contains(candidate) }
        }
        if (candidates.size + selected.size < record) {
            memo[selected] = 0
            return 0
        }

        return (candidates.maxOfOrNull { candidate ->
            biggestFullSubgraph((selected + candidate).sorted())
        } ?: selected.size).also {
            memo[selected] = it
            if (it > record) {
                bestSubgraph = selected
                record = it
            }
        }
    }

    fun part2(): String {
        uniqueNames.forEach { name ->
            biggestFullSubgraph(listOf(name))
        }
        return bestSubgraph.sorted().joinToString(",")
    }
}

fun main() {
    measured { Day23.part1() }
    measured { Day23.part2() }
}
