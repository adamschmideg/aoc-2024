import java.math.BigInteger

fun main() {
    val buttonRegex = Regex("""Button [AB]: X\+(\d+), Y\+(\d+)""")
    val prizeRegex = Regex("""Prize: X=(\d+), Y=(\d+)""")

    fun parseLine(line: String, regex: Regex): Pair<Int, Int> {
        val match = regex.find(line) ?: throw IllegalArgumentException("Invalid input format: $line")
        val x = match.groups[1]?.value?.toInt()!!
        val y = match.groups[2]?.value?.toInt()!!
        return Pair(x, y)
    }

    fun toIntOrZero(x: Double): Int {
        val intValue = x.toInt()
        if (x.compareTo(intValue) == 0) {
            return intValue
        }
        return 0
    }
    fun solveEquation(p: Int, q: Int, r: Int, s: Int, x: Int, y: Int): Pair<Int, Int> {
        val div: Double = (p * s - q * r).toDouble()
        if (div == 0.0) {
            return Pair(0, 0)
        }
        val a = toIntOrZero((x * s - y * r) / div)
        val b = toIntOrZero((x * q - y * p) / -div)
        return Pair(a, b)
    }

    fun countTokens(a: Int, b: Int): Int {
        return a * 3 + b
    }

    fun part1(input: List<String>): Int {
        var allTokens = 0
        for (chunk in input.chunked(4)) {
            val (lineA, lineB, linePrize, _) = chunk
            val (p, q) = parseLine(lineA, buttonRegex)
            val (r, s) = parseLine(lineB, buttonRegex)
            val (x, y) = parseLine(linePrize, prizeRegex)
            val (a, b) = solveEquation(p, q, r, s, x, y)
            val tokenCount = countTokens(a, b)
            // println("Parameters: $p, $q, $r, $s, $x, $y, $a, $b, $tokenCount")
            allTokens += tokenCount
        }
        return allTokens
    }

    val testInput = readInput("Day13_test")
    val input = readInput("Day13")

    val result1 = part1(testInput)
    check(result1 == 480, { "$result1" })

    println("Part 1: ${part1(input)}")
}
