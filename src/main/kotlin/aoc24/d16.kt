package aoc24

import java.io.File
import java.util.PriorityQueue

object Day16 {

    private data class Position(val x: Int, val y: Int)

    private data class Input(val map: List<String>, val start: Position, val end: Position)

    private val input = File("inputs/aoc24/d16.txt").readLines().let{
        Input(
            map = it,
            start = Position(it.first { it.contains("S") }.indexOf('S'), it.indexOfFirst { it.contains("S") }),
            end = Position(it.first { it.contains("E") }.indexOf('E'), it.indexOfFirst { it.contains("E") })
        )
    }

    private data class ScoredPosition(
        val x: Int,
        val y: Int,
        val score: Long,
        val path: List<Position>,
        val direction: Int
    )

    private data class Result(
        val bestScore: Long,
        val bestPaths: List<List<Position>>
    )

    private val result by lazy {
        val (map, start, end) = input
        val directions = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)
        val bestScores = Array(140) { Array(140) { LongArray(4) { Long.MAX_VALUE } } }
        val queue = PriorityQueue(compareBy<ScoredPosition> { it.score })
        val optimalPaths = mutableListOf<List<Position>>()
        var minScore = Long.MAX_VALUE

        directions.forEachIndexed { dir, _ ->
            queue.add(ScoredPosition(start.x, start.y, if (dir == 2) 0 else 1000, listOf(Position(start.x, start.y)), dir))
            bestScores[start.x][start.y][dir] = if (dir == 2) 0 else 1000
        }

        while (queue.isNotEmpty()) {
            val (x, y, score, path, dir) = queue.poll()
            if (score > bestScores[x][y][dir]) continue

            when {
                Position(x, y) != end -> {}
                score < minScore -> { minScore = score; optimalPaths.clear(); optimalPaths.add(path) }
                score == minScore -> optimalPaths += path
            }

            directions.forEachIndexed { newDir, (dx, dy) ->
                val new = Position(x + dx, y + dy)
                if (map[new.y][new.x] == '#') return@forEachIndexed

                val newScore = score + if (newDir == dir) 1 else 1001
                if (newScore <= bestScores[new.x][new.y][newDir]) {
                    bestScores[new.x][new.y][newDir] = newScore
                    queue.add(ScoredPosition(new.x, new.y, newScore, path + Position(new.x, new.y), newDir))
                }
            }
        }
        Result(minScore, optimalPaths)
    }

    fun part1() = result.bestScore

    fun part2() = result.bestPaths.flatten().toSet().size
}
