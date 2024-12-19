
fun main() {

    fun canMake(pattern: String, available: List<String>): Boolean {
        val stripes = available.joinToString("|")
        val regex = Regex("($stripes)*")
        val match = regex.matchEntire(pattern)
        return match != null
    }

    fun part1(input: List<String>): Int {
        var count = 0
        val available = input[0].split(", ")
        for (pattern in input.drop(2)) {
            if (canMake(pattern, available)) {
                count++
            }
        }
        return count
    }

    val testInput = readInput("Day19_test")
    val input = readInput("Day19")

    val result1 = part1(testInput)
    check(result1 == 6, { "$result1" })

    println("Part 1: ${part1(input)}")
}
