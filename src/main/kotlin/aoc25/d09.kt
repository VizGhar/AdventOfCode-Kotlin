package aoc25

import measured
import java.awt.Rectangle
import java.io.File
import kotlin.math.abs

object Day9 {

    fun part1(): Long {
        val points = File("inputs/aoc25/d9.txt").readLines().map { it.split(",").map { it.toLong() } }
        return (0..<points.size - 1).maxOf { i ->
            val (x1, y1) = points[i]
            (i + 1..<points.size).maxOf { j ->
                val (x2, y2) = points[j]
                (abs(x1 - x2) + 1) * (abs(y1 - y2) + 1)
            }
        }
    }

    data class Point(val x: Int, val y: Int)
    enum class Direction { L, U, R, D }

    fun part2(): Int {
        val initialPoints = File("inputs/aoc25/d9.txt").readLines().map { it.split(",").map { it.toInt() }.let { Point(it[0], it[1]) } }
        val topY = initialPoints.minOf { it.y }

        // adjusting so that search can start from top
        val startingPoint = initialPoints.filter { it.y == topY }.minBy { it.x }
        val adjustedPoints = (initialPoints.dropWhile { it != startingPoint } + initialPoints.dropLastWhile { it != startingPoint }).toMutableList()
        var direction = Direction.R

        // drawing rectangles around the edges
        val forbiddenRegions = mutableListOf<Rectangle>()
        var a = adjustedPoints.removeAt(0)
        var b = adjustedPoints.removeAt(0)
        var startSooner = true

        while (true) {
            val initialRect = when(direction) {
                Direction.L -> Rectangle(b.x + 1, a.y + 1, a.x - b.x - 1 + if (startSooner) 2 else 0, 1)
                Direction.U -> Rectangle(b.x - 1, b.y + 1, 1, a.y - b.y - 1 + if (startSooner) 2 else 0)
                Direction.R -> Rectangle(a.x + 1 + if (startSooner) -2 else 0, a.y - 1, b.x - a.x - 1 + if (startSooner) 2 else 0, 1)
                Direction.D -> Rectangle(a.x + 1, a.y + 1 + if (startSooner) -2 else 0, 1, b.y - a.y - 1+ if (startSooner) 2 else 0)
            }
            if (adjustedPoints.isEmpty()) break
            a = b
            b = adjustedPoints.removeAt(0)
            val nextDirection = when {
                b.y > a.y -> Direction.D
                b.y < a.y -> Direction.U
                b.x > a.x -> Direction.R
                b.x < a.x -> Direction.L
                else -> throw IllegalStateException()
            }

            startSooner = false
            when (listOf(direction, nextDirection)) {
                listOf(Direction.R, Direction.D) -> { initialRect.width += 2; startSooner = true }
                listOf(Direction.L, Direction.U) -> { initialRect.x -= 2; initialRect.width += 2; startSooner = true }
                listOf(Direction.D, Direction.L) -> { initialRect.height += 2; startSooner = true }
                listOf(Direction.U, Direction.R) -> { initialRect.y -= 2; initialRect.height += 2; startSooner = true }
            }
            forbiddenRegions += initialRect
            direction = nextDirection
        }

        var best = 0
        for (i in 0..<initialPoints.size-1){
            for (j in i+1..<initialPoints.size) {
                val a = initialPoints[i]
                val b = initialPoints[j]
                val rect = Rectangle(minOf(a.x, b.x), minOf(a.y, b.y), abs(a.x - b.x) + 1, abs(a.y - b.y) + 1)
                if (forbiddenRegions.none { it.intersects(rect) }) {
                    val area = rect.width * rect.height
                    if (area > best) best = area
                }
            }
        }
        return best
    }
}

fun main() {
    measured { Day9.part1() }
    measured { Day9.part2() }
}