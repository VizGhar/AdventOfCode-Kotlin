package aoc24

import measuredNano
import java.io.File

object Day14 {

    private const val width = 101
    private const val height = 103

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
        // If there is an image, somewhere should be too many filled points
        // in this case I made 10x10 buckets (100 elements), if there are at
        // least 50 elements in bucket - we found solution
        var state = input
        val buckets = mutableMapOf<Int, Int>()
        return (1..Int.MAX_VALUE).first {
            buckets.clear()
            state = state.next()
            for ((pos, _) in state) {
                val value = (buckets[pos.x / 10 + (pos.y / 10) * 100] ?: 0)
                if (value >= 50) return@first true
                buckets[pos.x / 10 + (pos.y / 10) * 100] = value + 1
            }
            false
        }
    }
}
