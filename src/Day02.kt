import kotlin.math.abs
import kotlin.math.sign

fun main() {
    fun isSafe(row: List<Int>): Boolean {
        val consecutiveDiffs = row.zipWithNext().map {
            Pair(abs(it.first - it.second),
                sign((it.second - it.first).toDouble())) }
        val allWithinRange = consecutiveDiffs.all { it.first in 1..3 }
        val allSameSign = consecutiveDiffs.all { it.second == consecutiveDiffs[0].second }
        return allWithinRange && allSameSign
    }

    check(!isSafe(listOf(1, 2, 7, 8, 9)))
    check(!isSafe(listOf(9, 7, 6, 2, 1)))
    check(!isSafe(listOf(1, 3, 2, 4, 5)))
    check(!isSafe(listOf(8, 6, 4, 4, 1)))

    check(isSafe(listOf(1, 3, 6, 7, 9)))

    fun part1(input: List<String>): Int {
        val listOfNumberList = input.map { it.split("\\s+".toRegex()).map { it.toInt() } }
        return listOfNumberList.filter { isSafe(it) }.count()
    }

    fun part2(input: List<String>): Int {
        return TODO()
    }

    val testInput = readInput("Day02_test")
    val result1 = part1(testInput)
    check(result1 == 2, { "2 != $result1" })

    val input = readInput("Day02")
    part1(input).println()
}
