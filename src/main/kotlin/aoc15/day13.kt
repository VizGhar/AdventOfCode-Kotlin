package aoc15

import measuredNano
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import kotlin.math.abs

object Day13 {

    data class Note(
        val who: String,
        val byWhom: String,
        val gain: Int
    )

    val input = File("inputs/aoc15/d13.txt").readLines().map {
        it.split(" ").let { Note(it[0], it[10].dropLast(1), it[3].toInt() * if (it[2] == "gain") 1 else -1) }
    }

    val names = input.map { it.who }.distinct()

    fun maximize(
        takenSeats: List<String>,
        names: List<String>,
        notes: List<Note>
    ) : Int {
        if (takenSeats.size == names.size) {
            return (takenSeats + takenSeats[0]).windowed(2).sumOf { (a, b) ->
                val gain1 = notes.first { it.who == a && it.byWhom == b }.gain
                val gain2 = notes.first { it.who == b && it.byWhom == a }.gain
                gain1 + gain2
            }
        }
        return (names - takenSeats).maxOf { name ->
            maximize(takenSeats + name, names, notes)
        }
    }

    var part1Result: Int = 0

    fun part1() = maximize(listOf(names[0]), names, input).also { part1Result = it }

    fun part2() = maximize(listOf(names[0]), names + " ", input + names.map { Note(it, " ", 0) } + names.map { Note(" ", it, 0) })

}

fun main() {
    measuredNano { Day13.part1() }
    measuredNano { Day13.part2() }
}
