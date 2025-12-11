package aoc25

import java.io.File

object Day12 {
    data class Case(val width: Int, val height: Int, val presentCounts: List<Int>)
    data class Shape(val id: Int, val desc: List<String>) {
        val weight = desc.sumOf { it.count { it == '#' } }
    }

    val file = File("inputs/aoc25/d12.txt").readLines()
    val cases = file.takeLastWhile { it.isNotBlank() }.map {
        Case(
            width = it.substringBefore("x").toInt(),
            height = it.substringAfter("x").substringBefore(":").toInt(),
            presentCounts = it.substringAfter(": ").split(" ").map { it.toInt() }
        )
    }

    val presents = file.dropLastWhile { it.isNotBlank() }.let {
        var remaining = it
        val result = mutableListOf<Shape>()
        while (remaining.isNotEmpty()) {
            result += Shape(
                remaining[0].substringBefore(":").toInt(),
                remaining.drop(1).takeWhile { it.isNotBlank() }
            )
            remaining = remaining.drop(2 + result.last().desc.size)
        }
        result
    }

    fun part1Slow(): Int {
        // can't fit on board no matter what - need more squares than board can have
        val filtered = cases.filter { case ->
            val tileArea = case.presentCounts.mapIndexed { index, count -> presents[index].weight * count }.sum()
            val boardArea = case.width * case.height
            boardArea >= tileArea
        }

        // trivial cases - considering each tile covers area 3x3 full (ignoring tile shapes)
        val trivialCases = filtered.count { case ->
            val numPerWidth = case.width / 3
            val numPerHeight = case.height / 3
            val num = case.presentCounts.sum()
            num <= numPerWidth * numPerHeight
        }

        return if (trivialCases == filtered.size) {
            trivialCases
        } else {
            throw IllegalStateException("Thank you Eric")
        }
    }

    fun part1() =
        cases.count { case ->
            case.presentCounts.sum() * 9 <= case.width * case.height
        }
}