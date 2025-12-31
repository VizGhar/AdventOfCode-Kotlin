package aoc16

import measuredNano
import java.io.File
import kotlin.math.log

object Day19 {

    // https://stackoverflow.com/a/7790373
    private fun highestBitToZero(originalValue: Int): Int {
        var mask = originalValue
        mask = mask or (mask shr 1)
        mask = mask or (mask shr 2)
        mask = mask or (mask shr 4)
        mask = mask or (mask shr 8)
        mask = mask or (mask shr 16)
        mask = mask shr 1
        return originalValue and mask
    }

    val input = File("inputs/aoc16/day19.txt").readText().toInt()

    fun part1() = highestBitToZero(input) * 2 + 1

    fun part2(): Int {
        val bases = intArrayOf(1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 59049, 177147, 531441, 1594323, 4782969)
        val groups = mutableListOf<Int>()
        val base = 3


        for (i in 1..250) {
            val remaining = (0..i).toMutableList()
            var position = 0
            while (remaining.size > 1) {
                var target = (position + remaining.size / 2) % remaining.size
                remaining.removeAt(target)
                position = (position + if (target > position) 1 else 0) % remaining.size
            }
            val theoryBase = bases.indexOfFirst { it > i }
            val intFrom = bases[theoryBase - 1]
            val intTo = bases[theoryBase]
            val mid = (bases[theoryBase - 1] + bases[theoryBase]) / 2

            println("$i -> ${remaining[0]}")
            if (i < mid) {
                println("Seems like -> ${i - intFrom}")
                if (i-intFrom != remaining[0]) error("XXX")
            } else {
                println("Seems like -> ${i/2 + 2*i-mid}")
            }

        }
        return 0
    }
}

fun main() {
    measuredNano { Day19.part1() }
    measuredNano { Day19.part2() }
}