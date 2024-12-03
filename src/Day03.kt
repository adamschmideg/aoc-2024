fun main() {

    fun evaluate(expression: String): Int {
        val regex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
        // Find not overlapping matches
        val matches = regex.findAll(expression)
        var accumulator = 0
        for (match in matches) {
            val a = match.groups[1]!!.value.toInt()
            val b = match.groups[2]!!.value.toInt()
            accumulator += a * b
        }
        return accumulator
    }

    fun part1(input: List<String>): Int {
        return evaluate(input.joinToString("\n"))
    }

    fun part2(input: List<String>): Int {
        val text = input.joinToString("\n")
        val skipRegex = """don't\(\).*?do\(\)""".toRegex()
        val cleanedText = skipRegex.replace(text, "")
        return evaluate(cleanedText)
    }

    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    val result1 = part1(testInput)
    check(result1 == 161, { "$result1" })
    part1(input).println()

    val testInput2 = listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")
    val result2 = part2(testInput2)
    check(result2 == 48, { "$result2" })

    part2(input).println()
}