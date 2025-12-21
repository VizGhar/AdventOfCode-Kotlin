package aoc24

import java.io.File
import kotlin.math.abs

object Day20 {

    private data class Position(val x: Int, val y: Int)

    private data class Input(val map: List<String>, val start: Position, val end: Position)

    private val input = File("inputs/aoc24/d20.txt").readLines().let {
        Input(
            map = it,
            start = Position(it.first { it.contains("S") }.indexOf('S'), it.indexOfFirst { it.contains("S") }),
            end = Position(it.first { it.contains("E") }.indexOf('E'), it.indexOfFirst { it.contains("E") })
        )
    }

    private fun findPath(ignore: Position = Position(-1, -1)): List<Position> {
        val directions = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)
        val expand = mutableListOf(listOf(input.start))
        val visited = mutableListOf<Position>()
        while (expand.isNotEmpty()) {
            val expandingPath = expand.removeFirst()
            val expanding = expandingPath.last()
            if (expanding in visited) continue
            visited += expanding
            if (expanding == input.end) {
                return expandingPath
            }
            directions.map { (dx, dy) ->
                if (expanding.x + dx !in 0..<input.map[0].length || expanding.y + dy !in 0..input.map.size) return@map
                if (input.map[expanding.y + dy][expanding.x + dx] == '#' && expanding.x + dx to expanding.y + dy != ignore) return@map
                expand += expandingPath + (Position(expanding.x + dx, expanding.y + dy))
            }
        }
        return emptyList()
    }

    private val path = findPath()

    private fun calc(allowedShortCutLength: Int, requiredSave: Int = 100) =
        path.sumOf { start ->
            val i = path.indexOf(start)
            path
                .drop(i + 1)
                .count { target ->
                    if (abs(target.x - start.x) + abs(target.y - start.y) > allowedShortCutLength) false
                    else path.size - (i + path.size - path.indexOfFirst{ it == target } + abs(target.x - start.x) + abs(target.y - start.y)) >= requiredSave
                }
        }

    fun part1() = calc(2)

    fun part2() = calc(20, 100)
}
