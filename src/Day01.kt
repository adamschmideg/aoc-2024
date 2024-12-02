import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val numberPairs = input.map { line ->
            // split line at consecutive spaces into two numbers and convert them to integers
            val (first, second) = line.split("\\s+".toRegex()).map { it.toInt() }
            Pair(first, second)
        }
        val firstColumnSorted = numberPairs.map { it.first }.sorted()
        val secondColumnSorted = numberPairs.map { it.second }.sorted()
        return firstColumnSorted.zip(secondColumnSorted).sumOf { abs(it.first - it.second) }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Test if implementation meets criteria from the description, like:
    val resultEasy = part1(listOf("2\t3"))
    check(resultEasy == 1, { "1 != $resultEasy" })

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    val result = part1(testInput)
    check(result == 11, { "11 != $result" })

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
