package aoc15

import measuredNano
import java.io.File
import kotlin.math.sqrt

object Day21 {

    val input = File("inputs/aoc15/d21.txt").readLines().let {
        Person(
            hp = it[0].substringAfter(": ").toInt(),
            damage = it[1].substringAfter(": ").toInt(),
            armor = it[2].substringAfter(": ").toInt()
        )
    }

    val shop = """
        Weapons:    Cost  Damage  Armor
        Dagger        8     4       0
        Shortsword   10     5       0
        Warhammer    25     6       0
        Longsword    40     7       0
        Greataxe     74     8       0

        Armor:      Cost  Damage  Armor
        Leather      13     0       1
        Chainmail    31     0       2
        Splintmail   53     0       3
        Bandedmail   75     0       4
        Platemail   102     0       5

        Rings:      Cost  Damage  Armor
        Damage +1    25     1       0
        Damage +2    50     2       0
        Damage +3   100     3       0
        Defense +1   20     0       1
        Defense +2   40     0       2
        Defense +3   80     0       3
    """.trimIndent()

    data class Item(val name: String, val cost: Int, val damage: Int, val armor: Int)

    val weapons = shop.split("\n").drop(1).take(5).map {
        it.split(" ").filter { it.isNotEmpty() }.let { Item(it[0], it[1].toInt(), it[2].toInt(), it[3].toInt()) }
    }

    val armor = shop.split("\n").drop(8).take(5).map {
        it.split(" ").filter { it.isNotEmpty() }.let { Item(it[0], it[1].toInt(), it[2].toInt(), it[3].toInt()) }
    }

    val rings = shop.split("\n").takeLast(6).map {
        it.split(" ").filter { it.isNotEmpty() }.let { Item("${it[0]} ${it[1]}", it[2].toInt(), it[3].toInt(), it[4].toInt()) }
    }

    val configurations by lazy {
        val configurations = mutableListOf<Person>()
        val armors = armor + Item("Armor-", 0, 0, 0)
        val rings = rings + Item("R1-", 0, 0, 0) + Item("R2-", 0, 0, 0)
        for (weapon in weapons) {
            for(armor in armors) {
                for (i in 0..rings.size-2) {
                    for (j in i+1..rings.size-1) {
                        configurations += Person(100, weapon.damage + rings[i].damage + rings[j].damage, armor.armor + rings[i].armor + rings[j].armor, weapon.cost + armor.cost + rings[i].cost + rings[j].cost)
                    }
                }
            }
        }
        configurations
    }

    data class Person(val hp: Int, val damage: Int, val armor: Int, val cost: Int = 0)

    fun duel(attacker: Person, defender: Person): Boolean {
        var defHp = defender.hp
        var attHp = attacker.hp
        while (true) {
            defHp -= maxOf(1, attacker.damage - defender.armor)
            if (defHp <= 0) return true
            attHp -= maxOf(1, defender.damage - attacker.armor)
            if (attHp <= 0) return false
        }
    }

    fun part1() = configurations.sortedBy { it.cost }
        .first { duel(it, input) }
        .cost

    fun part2() = configurations.sortedByDescending { it.cost }
        .first { !duel(it.copy(), input.copy()) }
        .cost

}

fun main() {
    measuredNano { Day21.part1() }
    measuredNano { Day21.part2() }
}

