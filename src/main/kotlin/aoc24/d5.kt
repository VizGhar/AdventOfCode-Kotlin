package aoc24

import java.io.File

object Day5 {

    private data class Input(
        val orders: List<Pair<Int, Int>>,
        val updates: List<List<Int>>
    )

    private val input = File("inputs/aoc24/d5.txt").readLines().let {
        Input(
            orders = it.takeWhile { it.contains("|") }.map { val (a, b) = it.split("|").map { it.toInt() }; a to b },
            updates = it.takeLastWhile { it.contains(",") }.map { it.split(",").toList().map { it.toInt() } }
        )
    }

    fun part1() = input.updates.filter { update ->
        input.orders
            .filter { it.first in update }
            .all { order ->
                val (a, b) = order
                update.indexOf(a) < (update.indexOf(b).takeIf { it != -1 } ?: Int.MAX_VALUE)
            }
    }.sumOf { it[it.size / 2] }

    fun part2() = input.updates.sumOf { update ->
        val mutable = update.toMutableList()
        while (true) {
            val (a, b) = input.orders.firstOrNull { order ->
                mutable.indexOf(order.first) > (mutable.indexOf(order.second).takeIf { it != -1 } ?: Int.MAX_VALUE)
            } ?: break
            val index1 = mutable.indexOf(a)
            val index2 = mutable.indexOf(b)
            mutable[index2] = a
            mutable[index1] = b
        }
        mutable.takeIf { it != update }?.let { it[it.size / 2] } ?: 0
    }
}
