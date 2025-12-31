package aoc16

import measuredNano
import java.io.File

object Day11 {

    data class Node(
        val pairs: List<Pair<Int, Int>>,
        val elevator: Int,
        val depth: Int
    )

    val pairs = File("inputs/aoc16/day11.txt").readLines().map { line ->
        val generators = "([^ ]*) generator".toRegex().findAll(line).toList().map { it.groupValues[1] }
        val microchips = "([^ ]*)-compatible microchip".toRegex().findAll(line).toList().map { it.groupValues[1] }
        generators to microchips
    }.let { floors ->
        val allGenerators = floors.map { it.first }.flatten()
        allGenerators.map { generator -> floors.indexOfFirst { it.first.contains(generator) } to floors.indexOfFirst { it.second.contains(generator) } }
    }

    fun solve(pairs: List<Pair<Int, Int>>) : Int {
        val toSolve = ArrayDeque<Node>()
        toSolve.add(Node(pairs.sortedWith(compareBy({ it.first }, { it.second })), 0, 0))

        val memo = mutableSetOf<Node>()

        while (toSolve.isNotEmpty()){
            val node = toSolve.removeFirst()
            val (currentPairs, currentElevator, currentDepth) = node

            val key = node.copy(pairs = currentPairs.sortedWith(compareBy({ it.first }, { it.second })), depth = 0)
            if (key in memo) { continue }
            memo.add(key)

            if (currentPairs.any { (generatorFloor, microchipFloor) -> microchipFloor != generatorFloor && currentPairs.any { it.first == microchipFloor } }) continue
            if (currentPairs.all { it.first == 3 && it.second == 3}) { return currentDepth }

            val synced = currentPairs.mapIndexed { i, floor -> listOfNotNull("G$i".takeIf { floor.first == currentElevator }, "M$i".takeIf { floor.second == currentElevator }) }.flatten()

            val singles = synced.map { listOf(it) }
            val pairs = (0..<synced.size - 1).map { a -> (a + 1..<synced.size).map { b -> listOf(synced[a], synced[b]) } }.flatten()
            val moves = singles + pairs

            val minFloor = currentPairs.minOf { minOf(it.first, it.second) }

            for (dir in listOf(-1, 1)) {
                val newFloor = currentElevator + dir
                if (newFloor !in minFloor..3) continue

                for (move in moves) {
                    val newPairs = currentPairs.toMutableList()
                    for (item in move) {
                        val index = item.drop(1).toInt()
                        if (item.startsWith("G")) newPairs[index] = newPairs[index].copy(first = newFloor)
                        else newPairs[index] = newPairs[index].copy(second = newFloor)
                    }
                    val newNode = Node(newPairs, newFloor, currentDepth + 1)
                    toSolve.add(newNode)
                }
            }
        }
        return -1
    }

    fun part1() = solve(pairs)

    fun part2() = solve(pairs + listOf(0 to 0, 0 to 0))
}

fun main() {
    measuredNano { Day11.part1() }
    measuredNano { Day11.part2() }
}