package aoc24

import java.io.File

object Day18 {

    private data class Position(val x: Int, val y: Int)

    private val size: Int = 70
    private val initial: Int = 1024

    private val input = File("inputs/aoc24/d18.txt").readLines().map { Position(it.substringBefore(",").toInt(), it.substringAfter(",").toInt()) }

    private fun shortestPath(locations: List<Position>): List<Position> {
        val directions = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)
        val expand = mutableListOf(listOf(Position(0, 0)))
        val visited = mutableListOf<Position>()
        while (expand.isNotEmpty()) {
            val expandingPath = expand.removeFirst()
            val expanding = expandingPath.last()
            if (expanding in visited) continue

            visited += expanding
            if (expanding == Position(size, size)) return expandingPath
            directions.map {  (dx, dy) ->
                if (expanding.x + dx !in 0.. size || expanding.y + dy !in 0..size) return@map
                if (Position(expanding.x + dx, expanding.y + dy) in locations) return@map
                expand += expandingPath + Position(expanding.x + dx, expanding.y + dy)
            }
        }
        return emptyList()
    }

    fun part1() = shortestPath(input.take(initial)).size - 1

    fun part2(): String {
        val obstacles = input.take(initial).toMutableList()
        var actualPath = shortestPath(obstacles)
        val additionalObstacles = input.drop(initial)
        for (additionalObstacle in additionalObstacles) {
            obstacles += additionalObstacle
            if (additionalObstacle in actualPath) {
                actualPath = shortestPath(obstacles)
                if (actualPath.isEmpty()) return "${additionalObstacle.x},${additionalObstacle.y}"
            }
        }
        return ""
    }
}

fun main() {
    println(Day18.part1())
    println(Day18.part2())
}