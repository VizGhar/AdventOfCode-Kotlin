package aoc25

import measured
import org.ojalgo.optimisation.ExpressionsBasedModel
import java.io.File

object Day10 {

    data class Schematics(val requirements: List<Boolean>, val presses: List<List<Int>>, val joltage: List<Int>)

    val schematicsAll = File("inputs/aoc25/d10.txt").readLines().map { line ->
        val requirements = line.substringBefore(" ").drop(1).dropLast(1).map { it == '#' }
        val presses = line.substringAfter(" ").substringBeforeLast(" ").split(" ").map { it.drop(1).dropLast(1).split(",").map { it.toInt() } }
        val joltage = line.substringAfterLast(" ").drop(1).dropLast(1).split(",").map { it.toInt() }
        Schematics(requirements, presses, joltage)
    }

    fun part1() = schematicsAll.sumOf { (requirements, presses) ->
        val activeConfigurations = mutableListOf(requirements.map { false } to 0L)
        val alreadyTested = mutableSetOf<List<Boolean>>()
        while (true) {
            val (current, depth) = activeConfigurations.removeAt(0)
            if (current == requirements) return@sumOf depth
            if (current in alreadyTested) continue
            alreadyTested += current
            for (press in presses) {
                activeConfigurations += current.mapIndexed { index, b -> if (index in press) !b else b } to depth + 1
            }
        }
        0L
    }

    fun part2() = schematicsAll.sumOf { (_, config, req) ->
        val n = config.size
        val m = req.size
        val model = ExpressionsBasedModel()
        val x = (0 until n).map { i ->
            model.addVariable("x_$i").lower(0).integer(true).weight(1.0)
        }

        for (j in 0 until m) {
            val expr = model.addExpression("req_$j")
            for (i in 0 until n) {
                if (j in config[i]) {
                    expr.set(x[i], 1)
                }
            }
            expr.level(req[j].toDouble())
        }

        val result = model.minimise()
        result.value.toInt()
    }
}

fun main() {
    measured { Day10.part1() }
    measured { Day10.part2() }
}