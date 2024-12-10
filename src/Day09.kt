fun main() {

    fun parseMap(line: String): List<Int?> {
        var index = 0
        var isFile = true
        val result = mutableListOf<Int?>()
        for (c in line) {
            val count = "$c".toInt()
            for (i in 0 until count) {
                if (isFile) {
                    result += index
                } else {
                    result += null
                }
            }
            if (isFile) {
                index++
            }
            isFile =!isFile
        }
        return result
    }

    fun rearrange(filesAndSpaces: List<Int?>): List<Int?> {
        val arr = filesAndSpaces.toTypedArray()
        while (true) {
            val indexOfLastNotNull = arr.indexOfLast { it != null }
            val indexOfFirstNull = arr.indexOfFirst { it == null }
            if (indexOfLastNotNull < indexOfFirstNull) {
                break
            }
            arr[indexOfFirstNull] = arr[indexOfLastNotNull]
            arr[indexOfLastNotNull] = null
        }
        return arr.toList()
    }

    fun compact(list: List<Any?>): String {
        return list.map { it?.toString() ?: "." }.joinToString("")
    }

    fun checksum(numbers: List<Int?>): Int {
        var checksum = 0
        for (i in numbers.indices) {
            val number = numbers[i]
            if (number!= null) {
                checksum += number * i
            }
        }
        return checksum
    }

    fun part1(input: List<String>): Int {
        val filesAndSpaces = parseMap(input[0])
        val arranged = rearrange(filesAndSpaces)
        val checksum = checksum(arranged)
        if (input[0].length < 20) {
            println("$input[0] ->")
            println("  ${compact(filesAndSpaces)}")
            println("  ${compact(arranged)}")
            println("  $checksum")
        }
        return checksum
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day09_test")
    val input = readInput("Day09")

    val testMap = "12345"
    val testParsedMap = "0..111....22222"
    val testGot1 = compact(parseMap(testMap))
    check(testParsedMap == testGot1, { testGot1 })

    val testArranged1 = compact(rearrange(parseMap(testMap)))
    val testExpectedArranged1 = "022111222......"
    check(testArranged1 == testExpectedArranged1, { testArranged1 })

    check(part1(testInput) == 1928, { "${part1(testInput)}" })

    // check(part2(testInput) == 123, { "${part2(testInput)}" })

    println("Part 1: ${part1(input)}")
    // println("Part 2: ${part2(input)}")
}
