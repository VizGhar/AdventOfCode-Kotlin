package aoc16

import measuredNano
import java.io.File
import java.security.MessageDigest

object Day5 {

    val input = File("inputs/aoc16/day5.txt")
        .readLines()[0]

    @OptIn(ExperimentalStdlibApi::class)
    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(this.toByteArray())
        return digest.toHexString()
    }

    fun part1(): String {
        var text = ""
        var hash: String
        for (id in 0..Int.MAX_VALUE) {
            hash = "${input}$id".md5()
            if (hash.startsWith("00000")) {
                text += hash[5]
            }
            if (text.length == 8) break
        }
        return text
    }

    fun part2() : String {
        val text = CharArray(8) { ' ' }
        var hash: String
        for (id in 0..Int.MAX_VALUE) {
            hash = "${input}$id".md5()
            if (hash.startsWith("00000")) {
                val pos = hash[5]
                val char = hash[6]
                if (pos.isDigit() && pos.digitToInt() in 0..7 && text[pos.digitToInt()] == ' ') {
                    text[pos.digitToInt()] = char
                }
            }
            if (text.none { it == ' ' }) break
        }
        return text.joinToString("")
    }
}

fun main() {
    measuredNano { Day5.part1() }
    measuredNano { Day5.part2() }
}