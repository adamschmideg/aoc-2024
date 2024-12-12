import java.math.BigInteger

fun main() {

    data class Cell(val x: Int, val y: Int)

    fun part1(input: List<String>): Int {
        var stones = input[0].split(" ").map{ it.toString().toBigInteger() }
        var newStones: List<BigInteger>
        for (round in 1..25) {
            newStones = mutableListOf()
            for (stone in stones) {
                if (stone == 0.toBigInteger()) {
                    newStones.add(1.toBigInteger())
                    continue
                }
                val stoneString = stone.toString()
                if (stoneString.length % 2 == 0) {
                    val left = stoneString.substring(0, stoneString.length / 2)
                    val right = stoneString.substring(stoneString.length / 2)
                    newStones.add(left.toBigInteger())
                    newStones.add(right.toBigInteger())
                    continue
                }
                newStones.add(stone * 2024.toBigInteger())
            }
            stones = newStones
            println("Round $round, size ${stones.size}")
        }
        return stones.size
    }

    val testInput = readInput("Day11_test")
    val input = readInput("Day11")

    // val result1 = part1(testInput)
    // check(result1.toInt() == 55312, { "$result1" })

    println("Part 1: ${part1(input)}")
}
