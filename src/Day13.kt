import java.math.BigDecimal
import java.math.BigInteger

fun main() {
    val buttonRegex = Regex("""Button [AB]: X\+(\d+), Y\+(\d+)""")
    val prizeRegex = Regex("""Prize: X=(\d+), Y=(\d+)""")

    fun parseLine(line: String, regex: Regex): Pair<BigInteger, BigInteger> {
        val match = regex.find(line) ?: throw IllegalArgumentException("Invalid input format: $line")
        val x = match.groups[1]?.value?.toBigInteger()!!
        val y = match.groups[2]?.value?.toBigInteger()!!
        return Pair(x, y)
    }

    fun divideOrZero(x: BigInteger, y: BigInteger): BigInteger {
        if (x % y != BigInteger.ZERO) {
            return BigInteger.ZERO
        }
        return x / y
    }

    fun solveEquation(p: BigInteger, q: BigInteger, r: BigInteger, s: BigInteger, x: BigInteger, y: BigInteger): Pair<BigInteger, BigInteger> {
        val div = p * s - q * r
        if (div == BigInteger.ZERO) {
            return Pair(BigInteger.ZERO, BigInteger.ZERO)
        }
        val a = divideOrZero(x * s - y * r, div)
        val b = divideOrZero(x * q - y * p, -div)
        return Pair(a, b)
    }

    fun countTokens(a: BigInteger, b: BigInteger): BigInteger {
        return a * BigInteger.valueOf(3) + b
    }

    fun part1(input: List<String>, addToX: BigInteger = BigInteger.ZERO, addToY: BigInteger = BigInteger.ZERO): BigInteger {
        var allTokens: BigInteger = BigInteger.ZERO
        for (chunk in input.chunked(4)) {
            val (lineA, lineB, linePrize, _) = chunk
            val (p, q) = parseLine(lineA, buttonRegex)
            val (r, s) = parseLine(lineB, buttonRegex)
            val (x, y) = parseLine(linePrize, prizeRegex)
            val (a, b) = solveEquation(p, q, r, s, x + addToX, y + addToY)
            if (addToX == BigInteger.ZERO || (a > BigInteger.valueOf(100) && b > BigInteger.valueOf(100))) {
                val tokenCount = countTokens(a, b)
                allTokens += tokenCount
            }
            // println("Parameters: $p, $q, $r, $s, $x, $y, $a, $b, $tokenCount")
        }
        return allTokens
    }

    fun part2(input: List<String>): BigInteger {
        val correction = BigInteger.valueOf(10000000000000)
        return part1(input, correction, correction)
    }

    val testInput = readInput("Day13_test")
    val input = readInput("Day13")

    val result1 = part1(testInput)
    check(result1.toInt() == 480, { "$result1" })

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
