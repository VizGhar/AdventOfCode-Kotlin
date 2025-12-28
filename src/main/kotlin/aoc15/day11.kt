package aoc15

import measuredNano
import java.io.File

object Day11 {
    val letters = 'a'..'z'
    val disallowedLetters = listOf('i', 'o', 'l')
    val String.hasStraight get() = this.windowed(3).any { it[2] == it[1] + 1 && it[1] == it[0] + 1 }
    val String.has2Doubles: Boolean get() {
        val candidates = windowed(2).withIndex().filter { it.value[0] == it.value[1] }
        return candidates.size == 2 && (candidates[1].index - candidates[0].index) > 1
    }
    val String.next: String get() {
        var result = ""
        for (i in length - 1 downTo 0) {
            if (this[i] + 1 > 'z') {
                result = "a${result}"
            } else {
                result = "${this.substring(0, i)}${this[i] + 1}$result"
                break
            }
        }
        return result
    }

    val input = File("inputs/aoc15/d11.txt").readText()

    fun part1(): String {
        var password = input
        while (true){
            if (password.has2Doubles && password.hasStraight && password.none { it in disallowedLetters }) break
            password = password.next
        }
        return password.also { part2Input = it }
    }

    var part2Input = ""

    fun part2(): String {
        var password = part2Input.ifEmpty { part1() }.next
        while (true){
            if (password.has2Doubles && password.hasStraight && password.none { it in disallowedLetters }) break
            password = password.next
        }
        return password
    }
}

fun main() {
    measuredNano { Day11.part1() }
    measuredNano { Day11.part2() }
}
