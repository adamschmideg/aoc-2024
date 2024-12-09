import java.math.BigInteger

fun main() {
    val plus: (BigInteger, BigInteger) -> BigInteger = { a, b -> a + b }
    val times: (BigInteger, BigInteger) -> BigInteger = { a, b -> a * b }
    val operators = arrayOf(plus, times)

    fun isProduceable(numbers: List<Int>, target: BigInteger): Boolean {
        val operatorBits = Math.pow(2.0, (numbers.size - 1).toDouble()).toInt()
        for (i in 0 until operatorBits) {
            var result = numbers[0].toBigInteger()
            for (j in 1 until numbers.size) {
                // Get jth bit of i
                val jthBit = (i shr (j - 1)) and 1
                val operator = operators[jthBit]
                result = operator(result, numbers[j].toBigInteger())
            }
            if (result == target) return true
        }
        return false
    }

    fun part1(input: List<String>): BigInteger {
        var total = 0.toBigInteger()
        for (line in input) {
            val (result, rest) = line.split(": ")
            val resultNumber = result.toBigInteger()
            val numbers = rest.split(" ").map { it.toInt() }
            if (isProduceable(numbers, resultNumber)) {
                total += resultNumber
            }
        }
        return total
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

    check(part1(testInput).toInt() == 3749, { "${part1(testInput)}" })

    println("Part 1: ${part1(input)}")
}
