package aoc24

import java.io.File

object Day14 {

    private val width = 101
    private val height = 103

    private data class Position(val x: Int, val y: Int)
    private data class Input(var position: Position, val velocity: Position)

    private val directions = listOf(Position(-1, 0), Position(0, -1), Position(1, 0), Position(0, 1))
    private val Position.neighbors get() = directions.map { Position(x + it.x, y + it.y) }

    private fun List<Input>.next() = map {
        it.copy(position = Position((it.position.x + it.velocity.x + width) % width, (it.position.y + it.velocity.y + height) % height))
    }

    private val input = File("inputs/aoc24/d14.txt").readLines().map {
        Input(
            position = Position(it.substringAfter("=").substringBefore(",").toInt(), it.substringAfter(",").substringBefore(" ").toInt()),
            velocity = Position(it.substringAfterLast("=").substringBefore(",").toInt(), it.substringAfterLast(",").substringBefore(" ").toInt())
        )
    }

    fun part1(): Int {
        var state = input
        repeat(100) { state = state.next() }
        return state.count { it.position.x < width / 2 && it.position.y < height / 2 } *
                state.count { it.position.x < width / 2 && it.position.y > height / 2 } *
                state.count { it.position.x > width / 2 && it.position.y < height / 2 } *
                state.count { it.position.x > width / 2 && it.position.y > height / 2 }
    }

    fun part2(): Int {
        var state = input
        for (i in 1..Int.MAX_VALUE) {
            state = state.next()
            val positions = state.map { it.position }
            if (positions.count { it.neighbors.any { n -> n in positions } } > state.size / 2) return i
        }
        return -1
    }
}
