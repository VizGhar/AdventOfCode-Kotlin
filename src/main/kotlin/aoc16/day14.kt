package aoc16

import measuredNano
import java.io.File
import java.security.MessageDigest

object Day14 {

    val input = File("inputs/aoc16/day14.txt").readText()

    private val symbols = mapOf('0' to 0, '1' to 1, '2' to 2, '3' to 3, '4' to 4, '5' to 5, '6' to 6, '7' to 7, '8' to 8, '9' to 9, 'a' to 10, 'b' to 11, 'c' to 12, 'd' to 13, 'e' to 14, 'f' to 15)

    private val md = MessageDigest.getInstance("MD5")

    @OptIn(ExperimentalStdlibApi::class)
    private fun String.md5(): String {
        val digest = md.digest(this.toByteArray())
        return digest.toHexString()
    }

    fun stretchedHash(text: String, stretchSize: Int = 0): String {
        var result = text.md5()
        repeat(stretchSize) {
            result = result.md5()
        }
        return result
    }

    fun solve(stretchSize: Int = 0): Int {
        var i = 0
        val keys = mutableSetOf<Int>()
        val row3occurences = Array(16) { mutableListOf<Int>()}
        var j: Int
        var k: Int
        var limit = Int.MAX_VALUE

        while (i < limit) {
            val hash = stretchedHash("$input$i", stretchSize)
            j = 0
            while (j < hash.length) {
                k = 1
                while (j + k < hash.length && hash[j + k] == hash[j]) k++
                if (k >= 5) {
                    for (row3 in row3occurences[symbols[hash[j]]!!]) if (row3 + 1000 >= i) keys += row3
                    row3occurences[symbols[hash[j]]!!].clear()
                    if (keys.size >= 64 && limit == Int.MAX_VALUE) limit = i + 1000
                }
                if (k >= 3) {
                    row3occurences[symbols[hash[j]]!!] += i
                    break
                }
                j += k
            }
            i++
        }
        return keys.sorted()[63]
    }

    fun part1() = solve(0)

    fun part2() = solve(2016)
}

fun main() {
    measuredNano { Day14.part1() }
    measuredNano { Day14.part2() }
}