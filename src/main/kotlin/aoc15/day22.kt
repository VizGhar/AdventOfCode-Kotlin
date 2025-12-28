package aoc15

import measuredNano
import java.io.File

object Day22 {

    val input = File("inputs/aoc15/d22.txt").readLines().let { it[0].substringAfter(": ").toInt() to it[1].substringAfter(": ").toInt() }

    data class Spell(val name: String, val cost: Int, val instantDamage: Int, val instantHeal: Int)
    data class Effect(val name: String, val cost: Int, val duration: Int, val armor: Int, val poison: Int, val mana: Int)

    val spells = listOf(Spell("Magic Missile", 53, 4, 0), Spell("Drain", 73, 2, 2))
    val effects = listOf(Effect("Shield", 113, 6, 7, 0, 0), Effect("Poison", 173, 6, 0, 3, 0), Effect("Recharge", 229, 5, 0, 0, 101))

    var best = Int.MAX_VALUE

    data class MemoKey(val playerTurn: Boolean, val playerMana: Int, val playerHp: Int, val bossHp: Int, val manaSpent: Int, val activeEffects: List<Effect>)
    val memo = mutableSetOf<MemoKey>()

    fun solve(hard: Boolean, playerTurn: Boolean, playerMana: Int, playerHp: Int, bossHp: Int, bossAttack: Int, manaSpent: Int, activeEffects: List<Effect>) {
        // pruning
        if (manaSpent > best) return

        // memoization
        val key = MemoKey(playerTurn, playerMana, playerHp, bossHp, manaSpent, activeEffects)
        if (!memo.add(key)) return

        // apply difficulty
        val newPlayerHp = if (playerTurn && hard) playerHp - 1 else playerHp
        if (newPlayerHp <= 0) return

        // apply effects
        val newBossHp = bossHp - activeEffects.sumOf { it.poison }
        val newPlayerMana = playerMana + activeEffects.sumOf { it.mana }
        val newActiveEffects = activeEffects.mapNotNull { if (it.duration == 1) null else it.copy(duration = it.duration - 1) }

        // boss dies -> log win
        if (newBossHp <= 0) {
            if (manaSpent < best) best = manaSpent
            return
        }

        if (!playerTurn) {
            val newPlayerHp = newPlayerHp - maxOf(1, bossAttack - newActiveEffects.sumOf { it.armor })
            if (newPlayerHp <= 0) return
            solve(hard, true, newPlayerMana, newPlayerHp, newBossHp, bossAttack, manaSpent, newActiveEffects)
        } else {
            // player turn - spells
            for (spell in spells) {
                if (spell.cost > newPlayerMana) continue
                solve(hard, false, newPlayerMana - spell.cost, newPlayerHp + spell.instantHeal, newBossHp - spell.instantDamage, bossAttack, manaSpent + spell.cost, newActiveEffects)
            }

            // player turn - effects
            for (effect in effects) {
                if (effect.cost > newPlayerMana) continue
                if (newActiveEffects.any { it.name == effect.name }) continue
                solve(hard, false, newPlayerMana - effect.cost, newPlayerHp, newBossHp, bossAttack, manaSpent + effect.cost, newActiveEffects + effect)
            }
        }
    }

    fun part1(): Int {
        best = Int.MAX_VALUE
        memo.clear()
        solve(false, true, 500, 50, input.first, input.second, 0, emptyList())
        return best
    }

    fun part2(): Int {
        best = Int.MAX_VALUE
        memo.clear()
        solve(true, true, 500, 50, input.first, input.second, 0, emptyList())
        return best
    }
}

fun main() {
    println("start")
    measuredNano { Day22.part1() }
    measuredNano { Day22.part2() }
}

