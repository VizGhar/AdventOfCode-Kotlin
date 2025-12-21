package aoc24

import java.io.File

object Day15 {

    private data class Position(val x: Int, val y: Int)

    private enum class Direction(val c: Char, val dx: Int, val dy: Int) {
        LEFT('<', -1, 0),
        TOP('^', 0, -1),
        RIGHT('>', 1, 0),
        BOTTOM('v', 0, 1)
    }

    private val Char.direction get() = Direction.entries.first { it.c == this }

    private data class Input(
        val startPos: Position,
        val walls: List<Position>,
        val boxes: List<Position>,
        val moves: List<Direction>,
        val boxWidth: Int
    )

    private operator fun Position.plus(direction: Direction) = Position(x + direction.dx, y + direction.dy)

    private val input = File("inputs/aoc24/d15.txt").readLines()

    private val input1 = Input(
        startPos = Position(input.first { it.contains("@") }.indexOf("@"), input.indexOfFirst { it.contains("@") }),
        walls = input.takeWhile { it.isNotEmpty() }.mapIndexed { y, s -> s.mapIndexedNotNull { x, c -> if (c == '#') Position(x, y) else null } }.flatten(),
        boxes = input.takeWhile { it.isNotEmpty() }.mapIndexed { y, s -> s.mapIndexedNotNull { x, c -> if (c == 'O') Position(x, y) else null } }.flatten(),
        moves = input.takeLastWhile { it.isNotEmpty() }.joinToString("").map { it.direction },
        boxWidth = 1
    )

    private val input2 = Input(
        startPos = Position(input1.startPos.x * 2, input1.startPos.y),
        walls = input1.walls.map { listOf(Position(it.x * 2, it.y), Position(it.x * 2 + 1, it.y)) }.flatten(),
        boxes = input1.boxes.map { Position(it.x * 2, it.y) },
        moves = input1.moves,
        boxWidth = 2
    )

    private fun tryMoveBoxes(boxes: List<Position>, walls: List<Position>, direction: Direction, position: Position, width: Int): Pair<List<Position>, Boolean> {
        var positions = listOf(position + direction)
        val move = mutableListOf<Position>()
        do {
            if (positions.any { it in walls }) return boxes to false
            val boxesToMove = boxes.filter { box -> box !in move && positions.any { position -> position.x in (box.x..<box.x+width) && position.y == box.y } }
            if (boxesToMove.isEmpty()) { break }
            move += boxesToMove
            positions = boxesToMove.map { box -> (0..<width).map { Position(box.x + direction.dx + Direction.RIGHT.dx * it, box.y + direction.dy) } }.flatten()
        } while (true)
        return boxes.map { if (it in move) it + direction else it } to true
    }

    private fun solver(input: Input) = run {
        val walls = input.walls
        var boxes = input.boxes

        var pos = input.startPos
        for (move in input.moves) {
            val (movedBoxes, moved) = tryMoveBoxes(boxes, walls, move, pos, input.boxWidth)
            if (moved) {
                boxes = movedBoxes
                pos += move
            }
        }
        boxes.sumOf { it.x + 100 * it.y }
    }

    fun part1() = solver(input1)

    fun part2() = solver(input2)
}
