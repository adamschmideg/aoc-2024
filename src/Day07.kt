import java.math.BigInteger

fun main() {
    val plus: (BigInteger, BigInteger) -> BigInteger = { a, b -> a + b }
    val times: (BigInteger, BigInteger) -> BigInteger = { a, b -> a * b }
    val concat: (BigInteger, BigInteger) -> BigInteger = { a, b -> (a.toString() + b.toString()).toBigInteger() }
    val operators = arrayOf(plus, times, concat)

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

    fun isProduceableWithRadix(numbers: List<Int>, target: BigInteger, radix: Int): Boolean {
        val operatorBits = Math.pow(radix.toDouble(), (numbers.size - 1).toDouble()).toInt()
        for (i in 0 until operatorBits) {
            var result = numbers[0].toBigInteger()
            val opIndexes = i.toString(radix).padStart(numbers.size - 1, '0')
            for (j in 1 until numbers.size) {
                try {
                    val index = opIndexes.substring(j - 1, j).toInt()
                    val operator = operators[index]
                    result = operator(result, numbers[j].toBigInteger())
                } catch (e: StringIndexOutOfBoundsException) {
                    println("Variables: $numbers, $operatorBits, $opIndexes, $j")
                    throw e
                }
            }
            if (result == target) return true
        }
        return false
    }
    fun part2(input: List<String>): BigInteger {
        var total = 0.toBigInteger()
        for (line in input) {
            val (result, rest) = line.split(": ")
            val resultNumber = result.toBigInteger()
            val numbers = rest.split(" ").map { it.toInt() }
            if (isProduceableWithRadix(numbers, resultNumber, 3)) {
                total += resultNumber
            }
        }
        return total
    }

    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

    check(part1(testInput).toInt() == 3749, { "${part1(testInput)}" })

    check(part2(testInput).toInt() == 11387, { "${part2(testInput)}" })

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
