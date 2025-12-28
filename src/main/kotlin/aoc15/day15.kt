package aoc15

import measuredNano
import java.io.File

object Day15 {

    data class Ingredient(
        val capacity: Int,
        val durability: Int,
        val flavor: Int,
        val texture: Int,
        val calories: Int
    )

    val input = File("inputs/aoc15/d15.txt").readLines().map {
        it.split(" ").let { Ingredient(
            it[2].dropLast(1).toInt(),
            it[4].dropLast(1).toInt(),
            it[6].dropLast(1).toInt(),
            it[8].dropLast(1).toInt(),
            it[10].toInt()
        ) }
    }

    // slow too much GC
    fun getBestScore(
        usedIngredients: List<Pair<Ingredient, Int>>,
        remainingIngredients: List<Ingredient>,
        remainingAmount: Int,
        countCalories: Boolean = false,
        remainingCalories: Int = 500
    ): Int {
        if (countCalories && remainingCalories < 0) return 0
        if (countCalories && remainingIngredients.isEmpty() && remainingCalories != 0) return 0
        if (remainingIngredients.isEmpty()) {
            var capacityScore = 0
            var durabilityScore = 0
            var flavorScore = 0
            var textureScore = 0
            var calories = 0
            usedIngredients.forEach { (ingredient, times) ->
                capacityScore += ingredient.capacity * times
                durabilityScore += ingredient.durability * times
                flavorScore += ingredient.flavor * times
                textureScore += ingredient.texture * times
                calories += ingredient.calories * times
            }
            return maxOf(0, capacityScore) * maxOf(0, durabilityScore) * maxOf(0, flavorScore) * maxOf(0, textureScore)
        }
        return (0..remainingAmount).maxOf {
            getBestScore(
                usedIngredients = usedIngredients + (remainingIngredients[0] to it),
                remainingIngredients = remainingIngredients.drop(1),
                remainingAmount = remainingAmount - it,
                countCalories = countCalories,
                remainingCalories = remainingCalories - remainingIngredients[0].calories * it
            )
        }
    }

    fun part1() = getBestScore(emptyList(), input, 100)

    fun part2() = getBestScore(emptyList(), input, 100, true)

}

fun main() {
    measuredNano { Day15.part1() }
    measuredNano { Day15.part2() }
}
