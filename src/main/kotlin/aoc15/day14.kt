package aoc15

import measuredNano
import java.io.File

object Day14 {

    data class Reindeer(
        val flySpeed: Int,
        val flySeconds: Int,
        val restSeconds: Int,

        var remainingSeconds: Int = flySeconds,
        var score: Int = 0,
        var isResting: Boolean = false,
        var travelled: Int = 0
    ) {
        fun travelledIn(seconds: Int): Int {
            var remainingSeconds = seconds
            var traveledDistance = 0
            var isResting = false
            while (remainingSeconds > 0) {
                when {
                    !isResting && remainingSeconds >= flySeconds -> {
                        traveledDistance += flySeconds * flySpeed
                        remainingSeconds -= flySeconds
                    }
                    !isResting -> {
                        traveledDistance += remainingSeconds * flySpeed
                        remainingSeconds = 0
                    }
                    else -> {
                        remainingSeconds = maxOf(0, remainingSeconds - restSeconds)
                    }
                }
                isResting = !isResting
            }
            return traveledDistance
        }

        fun tick() {
            if (!isResting) travelled += flySpeed
            if (--remainingSeconds == 0) {
                isResting = !isResting
                remainingSeconds = if (isResting) restSeconds else flySeconds
            }
        }
    }

    val input = File("inputs/aoc15/d14.txt").readLines().map {
        it.split(" ").let { Reindeer(it[3].toInt(), it[6].toInt(), it[13].toInt()) }
    }



    fun part1() = input.maxOf { it.travelledIn(2503) }

    fun part2(): Int {
        val reindeers = input
        repeat(2503) {
            var bestDistance = -1
            for (reindeer in reindeers) {
                reindeer.tick()
                if (reindeer.travelled > bestDistance) {
                    bestDistance = reindeer.travelled
                }
            }
            for (reindeer in reindeers) {
                if (reindeer.travelled == bestDistance) {
                    ++reindeer.score
                }
            }
        }
        return reindeers.maxOf { it.score }
    }

}

fun main() {
    measuredNano { Day14.part1() }
    measuredNano { Day14.part2() }
}
