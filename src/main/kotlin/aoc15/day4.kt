package aoc15

import measuredNano
import java.io.File
import java.security.MessageDigest

object Day4 {

    val input =  File("inputs/aoc15/d04.txt").readText()
    val md = MessageDigest.getInstance("MD5")

    fun find(from: Int, to: Int, size: Int) =
        (from..to).first{ md.digest("$input$it".toByteArray()).toHexString().startsWith("0".repeat(size)) }

    val p1 by lazy { find(0, Int.MAX_VALUE, 5) }
    val p2 by lazy { find(p1, Int.MAX_VALUE, 6) }

    fun part1() = p1
    fun part2() = p2

}

fun main() {
    measuredNano { Day4.part1() }
    measuredNano { Day4.part2() }
}