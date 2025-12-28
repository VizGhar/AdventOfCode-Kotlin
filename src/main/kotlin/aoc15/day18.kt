package aoc15

import measuredNano
import java.io.File

object Day18 {

    val input = File("inputs/aoc15/d18.txt").readLines()

    fun part1(): Int {
        val map = input.map { it.toCharArray() }
        val newMap = List(input.size) { CharArray(input[0].length) }
        repeat(100) {
            for (y in map.indices) {
                for (x in map[0].indices) {
                    val r = (-1..1).sumOf{ dy -> (-1..1).count { dx -> runCatching { map[y+dy][x+dx] == '#' }.getOrElse { false } } }
                    when {
                        map[y][x] == '#' && r in 3..4 -> newMap[y][x] = '#'
                        map[y][x] == '.' && r == 3 -> newMap[y][x] = '#'
                        else -> newMap[y][x] = '.'
                    }
                }
            }
            for (y in map.indices) {
                for (x in map[0].indices) {
                    map[y][x] = newMap[y][x]
                }
            }
        }
        return map.sumOf { it.count { it == '#' } }
    }

    fun part2() : Int {
        val map = input.map { it.toCharArray() }
        map[0][0] = '#'
        map[map.size - 1][0] = '#'
        map[0][map[0].size - 1] = '#'
        map[map.size - 1][map[0].size - 1] = '#'
        val newMap = List(input.size) { CharArray(input[0].length) }
        repeat(100) {
            for (y in map.indices) {
                for (x in map[0].indices) {
                    val r = (-1..1).sumOf{ dy -> (-1..1).count { dx -> runCatching { map[y+dy][x+dx] == '#' }.getOrElse { false } } }
                    when {
                        map[y][x] == '#' && r in 3..4 -> newMap[y][x] = '#'
                        map[y][x] == '.' && r == 3 -> newMap[y][x] = '#'
                        else -> newMap[y][x] = '.'
                    }
                }
            }
            for (y in map.indices) {
                for (x in map[0].indices) {
                    map[y][x] = newMap[y][x]
                }
            }

            map[0][0] = '#'
            map[map.size - 1][0] = '#'
            map[0][map[0].size - 1] = '#'
            map[map.size - 1][map[0].size - 1] = '#'
        }
        return map.sumOf { it.count { it == '#' } }
    }
}

fun main() {
    measuredNano { Day18.part1() }
    measuredNano { Day18.part2() }
}
