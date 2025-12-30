package aoc24

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
        measured { Day12.part2() }
        println("Day 13:")
        measured { Day13.part1() }
        measured { Day13.part2() }
        println("Day 14:")
        measured { Day14.part1() }
        measured { Day14.part2() }
        println("Day 15:")
        measured { Day15.part1() }
        measured { Day15.part2() }
        println("Day 16:")
        measured { Day16.part1() }
        measured { Day16.part2() }
        println("Day 17:")
        measured { Day17.part1() }
        measured { Day17.part2() }
        println("Day 18:")
        measured { Day18.part1() }
        measured { Day18.part2() }
        println("Day 19:")
        measured { Day19.part1() }
        measured { Day19.part2() }
        println("Day 20:")
        measured { Day20.part1() }
        measured { Day20.part2() }
        println("Day 21:")
        measured { Day21.part1() }
        measured { Day21.part2() }
        println("Day 22:")
        measured { Day22.part1() }
        measured { Day22.part2() }
        println("Day 23:")
        measured { Day23.part1() }
        measured { Day23.part2() }
        println("Day 24:")
        measured { Day24.part1() }
        measured { Day24.part2() }
        println("Day 25:")
        measured { Day25.part1() }
    }.also {
        println("All solved in ${it}ms")
    }
}