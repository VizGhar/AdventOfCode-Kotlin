package aoc15

import measuredNano
import java.io.File

object Day16 {

    val tape = Sue(
        children = 3,
        cats = 7,
        samoyeds = 2,
        pomeranians = 3,
        akitas = 0,
        vizslas = 0,
        goldfish = 5,
        trees = 3,
        cars = 2,
        perfumes = 1
    )

    data class Sue(
        val children: Int?,
        val cats: Int?,
        val samoyeds: Int?,
        val pomeranians: Int?,
        val akitas: Int?,
        val vizslas: Int?,
        val goldfish: Int?,
        val trees: Int?,
        val cars: Int?,
        val perfumes: Int?
    )

    val input = File("inputs/aoc15/d16.txt").readLines().map {
        Sue(
            children = if (it.contains("children")) it.substringAfter("children: ").substringBefore(",").toInt() else null,
            cats = if (it.contains("cats")) it.substringAfter("cats: ").substringBefore(",").toInt() else null,
            samoyeds = if (it.contains("samoyeds")) it.substringAfter("samoyeds: ").substringBefore(",").toInt() else null,
            pomeranians = if (it.contains("pomeranians")) it.substringAfter("pomeranians: ").substringBefore(",").toInt() else null,
            akitas = if (it.contains("akitas")) it.substringAfter("akitas: ").substringBefore(",").toInt() else null,
            vizslas = if (it.contains("vizslas")) it.substringAfter("vizslas: ").substringBefore(",").toInt() else null,
            goldfish = if (it.contains("goldfish")) it.substringAfter("goldfish: ").substringBefore(",").toInt() else null,
            trees = if (it.contains("trees")) it.substringAfter("trees: ").substringBefore(",").toInt() else null,
            cars = if (it.contains("cars")) it.substringAfter("cars: ").substringBefore(",").toInt() else null,
            perfumes = if (it.contains("perfumes")) it.substringAfter("perfumes: ").substringBefore(",").toInt() else null,
        )
    }

    fun part1() = input.withIndex().first { (index, sue) ->
        (sue.children == null || sue.children == tape.children) &&
        (sue.cats == null || sue.cats == tape.cats) &&
        (sue.samoyeds == null || sue.samoyeds == tape.samoyeds) &&
        (sue.pomeranians == null || sue.pomeranians == tape.pomeranians) &&
        (sue.akitas == null || sue.akitas == tape.akitas) &&
        (sue.vizslas == null || sue.vizslas == tape.vizslas) &&
        (sue.goldfish == null || sue.goldfish == tape.goldfish) &&
        (sue.trees == null || sue.trees == tape.trees) &&
        (sue.cars == null || sue.cars == tape.cars) &&
        (sue.perfumes == null || sue.perfumes == tape.perfumes)
    }.index + 1
    
    fun part2() = input.withIndex().first { (index, sue) ->
        (sue.children == null || sue.children == tape.children) &&
        (sue.cats == null || sue.cats > tape.cats!!) &&
        (sue.samoyeds == null || sue.samoyeds == tape.samoyeds) &&
        (sue.pomeranians == null || sue.pomeranians < tape.pomeranians!!) &&
        (sue.akitas == null || sue.akitas == tape.akitas) &&
        (sue.vizslas == null || sue.vizslas == tape.vizslas) &&
        (sue.goldfish == null || sue.goldfish < tape.goldfish!!) &&
        (sue.trees == null || sue.trees > tape.trees!!) &&
        (sue.cars == null || sue.cars == tape.cars) &&
        (sue.perfumes == null || sue.perfumes == tape.perfumes)
    }.index + 1
}

fun main() {
    measuredNano { Day16.part1() }
    measuredNano { Day16.part2() }
}
