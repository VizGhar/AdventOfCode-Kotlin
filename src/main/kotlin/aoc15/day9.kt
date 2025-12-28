package aoc15

import measuredNano
import java.io.File

object Day9 {

    val input = File("inputs/aoc15/d09.txt")
        .readLines()
        .map { it.split(" ").let { (from, _, to, _, dist) -> listOf((from to to) to dist.toInt(), (to to from) to dist.toInt())} }
        .flatten()
        .toMap()

    fun part1(): Int {
        val cities = (input.keys.map { it.first } + input.keys.map { it.first }).distinct()
        return cities.minOf { startingCity ->
            val toVisit = mutableListOf(listOf(startingCity) to 0)
            while (toVisit[0].first.size != cities.size) {
                val (currentPath, dist) = toVisit.removeAt(0)
                val remainingCities = cities - currentPath
                for (candidate in remainingCities) {
                    toVisit += currentPath + candidate to dist + (input[currentPath.last() to candidate] ?: throw IllegalStateException())
                }
                toVisit.sortBy { it.second }
            }
            toVisit[0].second
        }
    }

    fun part2(): Int {
        val cities = (input.keys.map { it.first } + input.keys.map { it.first }).distinct()
        return cities.maxOf { startingCity ->
            var max = 0
            val toVisit = mutableListOf(listOf(startingCity) to 0)
            while (toVisit.isNotEmpty()) {
                val (currentPath, dist) = toVisit.removeAt(0)
                if (currentPath.size == cities.size) {
                    if (dist > max) max = dist
                    continue
                }
                val remainingCities = cities - currentPath
                for (candidate in remainingCities) {
                    toVisit += currentPath + candidate to dist + (input[currentPath.last() to candidate] ?: throw IllegalStateException())
                }
                toVisit.sortBy { it.second }
            }
            max
        }
    }

}

fun main() {
    measuredNano { Day9.part1() }
    measuredNano { Day9.part2() }
}