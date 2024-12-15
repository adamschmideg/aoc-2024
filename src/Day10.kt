import java.math.BigInteger

fun main() {

    data class Cell(val x: Int, val y: Int)

    fun addToPositionIfInside(m: Array<Array<Int>>, cell: Cell, positions: MutableSet<Cell>, dx: Int, dy: Int, target: Int) {
       val x = cell.x + dx
       val y = cell.y + dy
       if (x >= 0 && x < m.size && y >= 0 && y < m[0].size) {
           if (target == 2) {
               // println("cell: $cell, dx: $dx, dy: $dy, target: $target, actual: ${m[x][y]}, new positions: $positions")

           }
           if (m[x][y] == target) {
               positions.add(Cell(x, y))
           }
       }
    }

    fun score(m: Array<Array<Int>>, cell: Cell): Int {
        var height = 1
        var positions = setOf(cell)
        var newPositions: Set<Cell>
        while (height <= 9) {
            // println("Target height: $height, Positions: $positions")
            newPositions = mutableSetOf()
            for (position in positions) {
                addToPositionIfInside(m, position, newPositions, 0, -1, height)
                addToPositionIfInside(m, position, newPositions, 1, 0, height)
                addToPositionIfInside(m, position, newPositions, 0, 1, height)
                addToPositionIfInside(m, position, newPositions, -1, 0, height)
            }
            positions = newPositions
            height++
        }
        return positions.size
    }

    fun part1(input: List<String>): Int {
        // Convert to matrix
        var m = Array(input.size) { Array(input[0].length) { 0 } }
        for (i in input.indices) {
            for (j in input[i].indices) {
                m[i][j] = input[i][j].toString().toInt()
            }
        }
        // Find zeroes
        val cells = mutableListOf<Cell>()
        for (i in m.indices) {
            for (j in 0 until m[i].size) {
                if (m[i][j] == 0) {
                    cells.add(Cell(i, j))
                }
            }
        }

        val result = cells.sumOf { score(m, it) }
        return result
    }

    val testInput = readInput("Day10_test")
    val input = readInput("Day10")

    val result1 = part1(testInput)
    check(result1 == 36, { "42!= $result1" })

    println("Part 1: ${part1(input)}")
}
