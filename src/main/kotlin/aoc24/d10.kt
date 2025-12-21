package aoc24

import java.io.File

object Day10 {

    private data class Position(val x: Int, val y: Int)

    private data class Input(
        val startingPositions: List<Position>,
        val map: List<String>
    )

    private val input = File("inputs/aoc24/d10.txt").readLines().let {
        Input(
            startingPositions = it.mapIndexed { y, line ->
                line.mapIndexedNotNull { x, c -> Position(x, y).takeIf { c == '0' } }
            }.flatten(),
            map = it
        )
    }

    private val allTrails = input.startingPositions.map { findTrails(it) }

    private data class ScoredPosition(val x: Int, val y: Int, val score: Int)

    private fun findTrails(start: Position): List<Position> {
        val positions = mutableListOf(ScoredPosition(start.x, start.y, -1))
        val results = mutableListOf<Position>()
        while (positions.isNotEmpty()) {
            val (x, y, s) = positions.removeFirst()
            if (x < 0 || y < 0 || y >= input.map.size || x >= input.map[0].length) continue
            val value = input.map[y][x].digitToInt()
            if (value != s + 1) continue
            if (value == 9) { results += Position(x, y); continue }
            positions += ScoredPosition(x + 1, y, value)
            positions += ScoredPosition(x - 1, y, value)
            positions += ScoredPosition(x, y + 1, value)
            positions += ScoredPosition(x, y - 1, value)
        }
        return results
    }

    fun part1() = allTrails.sumOf { it.distinct().size }
    
    fun part2() = allTrails.sumOf { it.size }

}
