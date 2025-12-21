package aoc24

import java.io.File

object Day13 {

    private data class Position(val x: Int, val y: Int)
    private data class LPosition(val x: Long, val y: Long)

    private data class Input(val buttonA: Position, val buttonB: Position, val prize: LPosition)

    private val input = File("inputs/aoc24/d13.txt").readLines().filter { it != "" }
        .chunked(3)
        .map { (a, b, p) ->
            Input(
                buttonA = Position(a.substringAfter("+").substringBefore(",").toInt(), a.substringAfterLast("+").toInt()),
                buttonB = Position(b.substringAfter("+").substringBefore(",").toInt(), b.substringAfterLast("+").toInt()),
                prize = LPosition(p.substringAfter("=").substringBefore(",").toLong(), p.substringAfterLast("=").toLong())
            )
        }

    private fun solve(input: Input): Long {
        val (a, b, p) = input
        val d1 = (a.x * p.y - a.y * p.x)
        val d2 = (b.y * p.x - b.x * p.y)
        val d3 = (a.x * b.y - a.y * b.x)
        return if (d1 % d3 != 0L || d2 % d3 != 0L) 0L
        else (d2 / d3) * 3 + d1 / d3
    }

    fun part1() = input.sumOf(::solve)
    
    fun part2() = input.map { it.copy(prize = LPosition(it.prize.x + 10000000000000L, it.prize.y + 10000000000000L)) }.sumOf(::solve)

}
