fun main() {

    fun hasWordTowards(word: String, input: List<String>, i: Int, j: Int, dx: Int, dy: Int): Boolean {
        if (word.isEmpty()) return true
        if (i < 0 || i >= input.size || j < 0 || j >= input[i].length) return false
        if (word[0]!= input[i][j]) return false
        return hasWordTowards(word.substring(1), input, i + dx, j + dy, dx, dy)
    }

    fun countWordFrom(word: String, input: List<String>, i: Int, j: Int): Int {
        var count = 0
        for (x in -1..1) {
            for (y in -1..1) {
                if (hasWordTowards(word, input, i, j, x, y)) {
                    count++
                }
            }
        }

        return count
    }

    fun part1(input: List<String>): Int {
        var count = 0
        for (i in input.indices) {
            for (j in 0 until input[i].length) {
                count += countWordFrom("XMAS", input, i, j)
            }
        }
        return count
    }

    fun countCrossWordFrom(center: Char, before: Char, after: Char, input: List<String>, i: Int, j: Int): Int {
        if (input[i][j]!= center) return 0

        var count = 0
        for (x in -1..1) {
            for (y in -1..1) {
                if (input[i + x][j + y] == before && input[i - x][j - y] == after &&
                    input[i + y][j - x] == before && input[i - y][j + x] == after) {
                    count++
                }
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        for (i in 1..<input.size - 1) {
            for (j in 1 until input[i].length - 1) {
                count += countCrossWordFrom('A', 'M', 'S', input, i, j)
            }
        }
        return count
    }

    val smallInput = """
        |..X...
        |.SAMX.
        |.A..A.
        |XMAS.S
        |.X....""".trimMargin("|").split("\n")

    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    check(part1(testInput) == 18, { "${part1(testInput)}" })

    check(part2(testInput) == 9, { "${part2(testInput)}" })

    part1(input).println()
    part2(input).println()
}
