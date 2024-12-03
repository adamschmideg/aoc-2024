fun main() {

    fun part1(input: List<String>): Int {
        val text = input[0]
        val regex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
        // Find not overlapping matches
        val matches = regex.findAll(text)
        var accumulator = 0
        for (match in matches) {
            val a = match.groups[1]!!.value.toInt()
            val b = match.groups[2]!!.value.toInt()
            accumulator += a * b
        }
        return accumulator
    }

    fun part2(input: List<String>): Int {
        return TODO()
    }

    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    val result1 = part1(testInput)
    check(result1 == 161, { "$result1" })
    part1(input).println()

    val result2 = part2(testInput)
    check(result2 == 4, { "4!= $result2" })

    part2(input).println()
}