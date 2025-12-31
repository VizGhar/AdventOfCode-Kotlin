package aoc16

import measuredNano
import java.io.File

object Day15 {

    data class Line(val id: Int, val positions: Int, val initialPosition: Int)

    val input = File("inputs/aoc16/day15.txt").readLines().map { line -> Line(
        id = line.substringAfter("#").substringBefore(" ").toInt(),
        positions = line.substringAfter(" has ").substringBefore(" ").toInt(),
        initialPosition = line.substringAfter("position ").substringBefore(".").toInt()
    ) } + Line(7, 11, 0)

    var part1 = 0
    var part2 = 0

    fun solve() {
        val starts = input.map { line -> ((line.positions - (line.initialPosition + line.id)) % line.positions + line.positions) % line.positions to line.positions }
        var i = 0
        var (a, ai) = starts[0]
        while (++i < input.size) {
            var (b, bi) = starts[i]
            while (a != b) {
                if (a < b) a += ai
                else b += bi
            }
            if (i == input.size - 2) part1 = a
            if (i == input.size - 1) part2 = a
            ai *= bi
        }
    }

    fun part1(): Int {
        if (part1 == 0) solve()
        return part1
    }

    fun part2(): Int {
        if (part2 == 0) solve()
        return part2
    }

}

fun main() {
    measuredNano { Day15.part1() }
    measuredNano { Day15.part2() }
}