package aoc15

import measuredNano
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun main() {
    measureNanoTime {
        measuredNano { Day1.part1() }
        measuredNano { Day1.part2() }
        measuredNano { Day2.part1() }
        measuredNano { Day2.part2() }
        measuredNano { Day3.part1() }
        measuredNano { Day3.part2() }
        measuredNano { Day4.part1() }
        measuredNano { Day4.part2() }
        measuredNano { Day5.part1() }
        measuredNano { Day5.part2() }
        measuredNano { Day6.part1() }
        measuredNano { Day6.part2() }
        measuredNano { Day7.part1() }
        measuredNano { Day7.part2() }
        measuredNano { Day8.part1() }
        measuredNano { Day8.part2() }
        measuredNano { Day9.part1() }
        measuredNano { Day9.part2() }
        measuredNano { Day10.part1() }
        measuredNano { Day10.part2() }
        measuredNano { Day11.part1() }
        measuredNano { Day11.part2() }
        measuredNano { Day12.part1() }
        measuredNano { Day13.part1() }
        measuredNano { Day13.part2() }
        measuredNano { Day14.part1() }
        measuredNano { Day14.part2() }
        measuredNano { Day15.part1() }
        measuredNano { Day15.part2() }
        measuredNano { Day16.part1() }
        measuredNano { Day16.part2() }
        measuredNano { Day17.part1() }
        measuredNano { Day17.part2() }
        measuredNano { Day18.part1() }
        measuredNano { Day18.part2() }
        measuredNano { Day19.part1() }
        measuredNano { Day19.part2() }
        measuredNano { Day20.part1() }
        measuredNano { Day20.part2() }
        measuredNano { Day21.part1() }
        measuredNano { Day21.part2() }
        measuredNano { Day22.part1() }
        measuredNano { Day22.part2() }
        measuredNano { Day23.part1() }
        measuredNano { Day23.part2() }
        measuredNano { Day24.part1() }
        measuredNano { Day24.part2() }
        measuredNano { Day25.part1() }
    }.also {
        println("All solved in ${it/1000000.0}ms")
    }
}