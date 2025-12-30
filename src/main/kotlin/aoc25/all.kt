package aoc25

import measured
import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis {
        println("Day 1:")
        measured { Day1.part1() }
        measured { Day1.part2() }
        println("Day 2:")
        measured { Day2.part1() }
        measured { Day2.part2() }
        println("Day 3:")
        measured { Day3.part1() }
        measured { Day3.part2() }
        println("Day 4:")
        measured { Day4.part1() }
        measured { Day4.part2() }
        println("Day 5:")
        measured { Day5.part1() }
        measured { Day5.part2() }
        println("Day 6:")
        measured { Day6.part1() }
        measured { Day6.part2() }
        println("Day 7:")
        measured { Day7.part1() }
        measured { Day7.part2() }
        println("Day 8:")
        measured { Day8.part1() }
        measured { Day8.part2() }
        println("Day 9:")
        measured { Day9.part1() }
        measured { Day9.part2() }
        println("Day 10:")
        measured { Day10.part1() }
        measured { Day10.part2() }
        println("Day 11:")
        measured { Day11.part1() }
        measured { Day11.part2() }
        println("Day 12:")
        measured { Day12.part1() }
    }.also {
        println("All solved in ${it}ms")
    }
}