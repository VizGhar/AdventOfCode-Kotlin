package aoc15

import measuredNano
import java.io.File
import java.math.BigDecimal

object Day24 {
    val results = mutableListOf<List<Long>>()

    fun possibleGroups(
        lst: List<Long>,
        index: Int = 0,
        used: List<Long> = listOf(),
        remainingWeight: Long = lst.sum() / 3
    ) {
        if (index >= lst.size || (index..<lst.size).sumOf { lst[it] } < remainingWeight) return
        for (i in index..<lst.size) {
            if (lst[i] > remainingWeight) break
            if (lst[i] == remainingWeight) {
                results += used + lst[i]; break
            }
            possibleGroups(lst, i + 1, used + lst[i], remainingWeight - lst[i])
        }
    }

    fun groupEm(possibleGroups: List<List<Long>>): BigDecimal {
        val maxValue = possibleGroups.maxOf { it.fold(BigDecimal(1)) { a, b -> a * b.toBigDecimal() } }
        val possibleGroups =
            possibleGroups.sortedBy { BigDecimal(it.size) * maxValue + it.fold(BigDecimal(1)) { a, b -> a * b.toBigDecimal() } }
        for (i in 0..possibleGroups.size - 3) {
            for (j in i + 1..possibleGroups.size - 2) {
                if (possibleGroups[j].any { it in possibleGroups[i] }) continue
                for (k in j + 1..possibleGroups.size - 1) {
                    if (possibleGroups[k].any { it in possibleGroups[i] }) continue
                    if (possibleGroups[k].any { it in possibleGroups[j] }) continue
                    return possibleGroups[i].fold(BigDecimal(1)) { a, b -> a * b.toBigDecimal() }
                }
            }
        }
        return BigDecimal.ZERO
    }

    fun groupEm2(possibleGroups: List<List<Long>>): BigDecimal {
        val maxValue = possibleGroups.maxOf { it.fold(BigDecimal(1)) { a, b -> a * b.toBigDecimal() } }
        val possibleGroups =
            possibleGroups.sortedBy { BigDecimal(it.size) * maxValue + it.fold(BigDecimal(1)) { a, b -> a * b.toBigDecimal() } }
        for (i in 0..possibleGroups.size - 4) {
            for (j in i + 1..possibleGroups.size - 3) {
                if (possibleGroups[j].any { it in possibleGroups[i] }) continue
                for (k in j + 1..possibleGroups.size - 2) {
                    if (possibleGroups[k].any { it in possibleGroups[i] }) continue
                    if (possibleGroups[k].any { it in possibleGroups[j] }) continue
                    for (l in k + 1..possibleGroups.size - 1) {
                        if (possibleGroups[l].any { it in possibleGroups[i] }) continue
                        if (possibleGroups[l].any { it in possibleGroups[j] }) continue
                        if (possibleGroups[l].any { it in possibleGroups[k] }) continue
                        return possibleGroups[i].fold(BigDecimal(1)) { a, b -> a * b.toBigDecimal() }
                    }
                }
            }
        }
        return BigDecimal.ZERO
    }

    val weights = File("inputs/aoc15/d24.txt").readLines().map { it.toLong() }

    fun part1(): BigDecimal {
        results.clear()
        possibleGroups(weights.sorted())
        return groupEm(results)
    }

    fun part2(): BigDecimal {
        results.clear()
        possibleGroups(weights.sorted(), 0, emptyList(), weights.sum() / 4)
        return groupEm2(results)
    }
}

fun main() {
    measuredNano { Day24.part1() }
    measuredNano { Day24.part2() }
}