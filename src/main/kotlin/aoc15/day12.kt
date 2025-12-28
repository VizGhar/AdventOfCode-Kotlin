package aoc15

import measuredNano
import org.json.JSONArray
import org.json.JSONObject
import java.io.File


object Day12 {

    val input = JSONObject(File("inputs/aoc15/d12.txt").readText())

    fun getValue(value: Any, ignoreRed: Boolean) : Int {
        return when (value) {
            is Int -> value
            is JSONObject -> sum(value, ignoreRed)
            is JSONArray -> value.sumOf { getValue(it, ignoreRed) }
            else -> 0
        }
    }

    fun sum(obj: JSONObject, ignoreRed: Boolean) =
        if (ignoreRed && obj.names().any { obj.get(it.toString()) == "red" }) { 0 }
        else obj.names().sumOf { getValue(obj.get(it.toString()), ignoreRed) }

    fun part1() = sum(input, false)

    fun part2() = sum(input, true)

}

fun main() {
    measuredNano { Day12.part1() }
    measuredNano { Day12.part2() }
}
