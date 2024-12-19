
fun main() {
    val buttonRegex = Regex("""Button [AB]: X\+(\d+), Y\+(\d+)""")
    val prizeRegex = Regex("""Prize: X=(\d+), Y=(\d+)""")

    fun parseLine(line: String, regex: Regex): Pair<Long, Long> {
        val match = regex.find(line) ?: throw IllegalArgumentException("Invalid input format: $line")
        val x = match.groups[1]?.value?.toLong()!!
        val y = match.groups[2]?.value?.toLong()!!
        return Pair(x, y)
    }

    fun toLongOrZero(x: Double): Long {
        val intValue = x.toLong()
        if (x.compareTo(intValue) == 0) {
            return intValue
        }
        return 0
    }
    fun solveEquation(p: Long, q: Long, r: Long, s: Long, x: Long, y: Long): Pair<Long, Long> {
        val div: Double = (p * s - q * r).toDouble()
        if (div == 0.0) {
            return Pair(0, 0)
        }
        val a = toLongOrZero((x * s - y * r) / div)
        val b = toLongOrZero((x * q - y * p) / -div)
        return Pair(a, b)
    }

    fun countTokens(a: Long, b: Long): Long {
        return a * 3 + b
    }

    fun part1(input: List<String>, addToX: Long = 0, addToY: Long = 0): Long {
        var allTokens: Long = 0
        for (chunk in input.chunked(4)) {
            val (lineA, lineB, linePrize, _) = chunk
            val (p, q) = parseLine(lineA, buttonRegex)
            val (r, s) = parseLine(lineB, buttonRegex)
            val (x, y) = parseLine(linePrize, prizeRegex)
            val (a, b) = solveEquation(p, q, r, s, x + addToX, y + addToY)
            val tokenCount = countTokens(a, b)
            // println("Parameters: $p, $q, $r, $s, $x, $y, $a, $b, $tokenCount")
            allTokens += tokenCount
        }
        return allTokens
    }

    fun part2(input: List<String>): Long {
        val correction = 10000000000000
        return part1(input, correction, correction)
    }

    val testInput = readInput("Day13_test")
    val input = readInput("Day13")

    val result1 = part1(testInput)
    check(result1.toInt() == 480, { "$result1" })

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
