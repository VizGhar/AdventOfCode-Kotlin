package aoc24

import java.io.File

object Day12 {

    private data class Position(val x: Int, val y: Int)

    private val input = File("inputs/aoc24/d12.txt").readLines()

    private val directions = listOf(Position(-1, 0), Position(0, -1), Position(1, 0), Position(0, 1))
    private fun Position.neighbors() = directions.map { Position(x + it.x, y + it.y) }

    private fun getRegion(x: Int, y: Int): Set<Position> {
        val visited = mutableSetOf<Position>()
        val solve = mutableListOf(Position(x, y))
        while (solve.isNotEmpty()) {
            val actual = solve.removeAt(0)
            if (actual.x < 0 || actual.y < 0 ||
                actual.x >= input.size || actual.y >= input[0].length ||
                input[actual.y][actual.x] != input[y][x] ||
                actual in visited
            ) continue
            visited += actual
            solve += actual.neighbors()
        }
        return visited
    }

    private fun countDistinctSides(region: Set<Position>, lookup: Position) =
        // filter sides - example: to right side of fence is region to left not
        region.filter { !region.contains(Position(it.x + lookup.x, it.y + lookup.y)) }
            // group by side - example: all left fences at x == 1, 2, 3 ...
            .groupBy { if (lookup.y == 0) it.x else it.y }
            .values.sumOf { withSpaces ->
                // count distinct fences (spaces + 1) - example: y = {1,2,4,5,6} -> {1,2} and {4,5,6}
                withSpaces.sortedBy { if (lookup.y == 0) it.y else it.x }.zipWithNext()
                    .count { (a, b) -> if (lookup.y == 0) (b.y != a.y + 1) else (b.x != a.x + 1) } + 1
            }

    private fun compute(price: (Set<Position>) -> Int) = with(mutableSetOf<Position>()) {
        input.indices.sumOf { y ->
            input[0].indices.sumOf { x ->
                if (Position(x, y) in this) 0
                else price(getRegion(x, y).also { addAll(it) })
            }
        }
    }

    fun part1() = compute { region ->
        region.sumOf { positions -> positions.neighbors().count { it !in region } } * region.size
    }
    
    fun part2() = compute { region ->
        directions.sumOf { lookup -> countDistinctSides(region, lookup) } * region.size
    }

}
