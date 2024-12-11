import java.math.BigInteger

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

    fun checksum(numbers: List<Int?>): BigInteger {
        var checksum = 0.toBigInteger()
        for (i in numbers.indices) {
            val number = numbers[i]
            if (number!= null) {
                checksum += (number * i).toBigInteger()
            }
        }
        return checksum
    }

    fun part1(input: List<String>): BigInteger {
        val filesAndSpaces = parseMap(input[0])
        val arranged = rearrange(filesAndSpaces)
        val checksum = checksum(arranged)
        return checksum
    }

    fun rearrangeBetter(numbers: List<Int?>): List<Int?> {
        val arr: Array<Int?> = numbers.toTypedArray()
        var blockStart = arr.size
        var blockEnd: Int
        var currentNumber: Int = -1
        var freeSpaceStart: Int
        var freeSpaceEnd = 0
        var hasFreeSpace: Boolean
        while (true) {
            // Find last block
            blockEnd = blockStart
            while (blockEnd >= 0) {
                blockEnd--
                if (arr[blockEnd] != null) {
                    currentNumber = arr[blockEnd]!!
                    break
                }
            }
            if (blockEnd <= 0) {
                break
            }

            // Find beginning of consecutive blocks
            blockStart = blockEnd
            while (blockStart > 0 && arr[blockStart] == currentNumber) {
                blockStart--
            }
            blockStart++

            // Find free space large enough to fit the block
            hasFreeSpace = false
            freeSpaceStart = -1
            val blockSize = blockEnd - blockStart + 1
            var freeSpaceSize = 0
            while (freeSpaceStart < blockStart) {
                freeSpaceStart++
                if (arr[freeSpaceStart] == null) {
                    // Find end of free space
                    freeSpaceEnd = freeSpaceStart
                    while (freeSpaceEnd < blockEnd && arr[freeSpaceEnd] == null) {
                        freeSpaceEnd++
                    }
                    freeSpaceSize = freeSpaceEnd - freeSpaceStart
                    if (freeSpaceSize >= blockSize) {
                        hasFreeSpace = true
                        break
                    }
                }
            }
            println("Block: $blockStart-$blockEnd, $blockSize, currentNumber: $currentNumber")
            println("Free space: $freeSpaceStart-$freeSpaceEnd, $freeSpaceSize, hasFreeSpace: $hasFreeSpace")
            println("Array before: ${compact(arr.toList())}")
            // Move block to free space
            if (hasFreeSpace) {
                for (i in 0 until blockSize) {
                    arr[freeSpaceStart + i] = currentNumber
                    arr[blockStart + i] = null
                }
            }
            println("Array after:  ${compact(arr.toList())}")
            println()
        }
        return arr.toList()
    }

    fun part2(input: List<String>): BigInteger {
        val filesAndSpaces = parseMap(input[0])
        val arranged = rearrangeBetter(filesAndSpaces)
        val checksum = checksum(arranged)
        return checksum
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

    check(part1(testInput).toInt() == 1928, { "${part1(testInput)}" })

    val testGot2 = part2(testInput).toInt()
    check(testGot2 == 2858, { "$testGot2" })
    // check(part2(testInput) == 123, { "${part2(testInput)}" })

    // println("Part 1: ${part1(input)}")
    // println("Part 2: ${part2(input)}")
}
