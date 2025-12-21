package aoc24

import java.io.File

object Day7 {

    private data class Equation(val result: Long, val operands: List<Long>)

    private val input = File("inputs/aoc24/d7.txt").readLines().map {
        Equation(
            it.substringBefore(":").toLong(),
            it.substringAfter(": ").split(" ").map { it.toLong() }
        )
    }

    private fun isViable(
        result: Long,
        current: Long,
        operands: List<Long>,
        index: Int,
        availableOperators: List<(Long, Long) -> Long>
    ): Boolean {
        if (current == result && index == operands.size) return true
        if (current > result || index == operands.size) return false
        if (index == 0) return isViable(result, operands[0], operands, 1, availableOperators)
        return availableOperators.any { operator ->
            isViable(result, operator(current, operands[index]), operands, index + 1, availableOperators)
        }
    }

    private fun solution(operators: List<(Long, Long) -> Long>) =
        input
            .map { if (isViable(it.result, 0, it.operands, 0, operators)) it.result else 0L }
            .sumOf { it }
            .toString()

    private val operatorsA: List<(Long, Long) -> Long> = listOf(
        { a, b -> a + b },
        { a, b -> a * b }
    )

    private val operatorsB = operatorsA + { a, b -> (a.toString() + b.toString()).toLong() }

    fun part1() = solution(operatorsA)

    fun part2() = solution(operatorsB)
}
