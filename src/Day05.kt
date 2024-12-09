fun main() {
    fun indexPageNumbers(pageNumbers: List<Int>): Map<Int, Int> {
        val index = mutableMapOf<Int, Int>()
        for (i in pageNumbers.indices) {
            index[pageNumbers[i]] = i
        }
        return index
    }

    fun conformsToRules(orderingRules: Map<Int,Set<Int>>, pageNumbers: List<Int>): Boolean {
        val index = indexPageNumbers(pageNumbers)
        for (pre in orderingRules.keys) {
            for (post in orderingRules.getOrDefault(pre, setOf())) {
                if (pre in index && post in index && index[pre]!! > index[post]!!) {
                    return false
                }
            }
        }
        return true
    }


    fun parseManual(input: List<String>): Pair<Map<Int, Set<Int>>, List<List<Int>>> {
        val orderingRules = mutableMapOf<Int, Set<Int>>()
        val pageNumbersList = mutableListOf<List<Int>>()

        for (line in input) {
            if (line.contains("|")) {
                val (pre, post) = line.split("|").map { it.toInt() }
                orderingRules[pre] = orderingRules.getOrDefault(pre, setOf()) + post
            } else if (line.isBlank()) {
                continue
            } else {
                val pageNumbers = line.split(",").map { it.toInt() }
                pageNumbersList.add(pageNumbers)
            }
        }

        return Pair(orderingRules, pageNumbersList)
    }

    fun getMiddleNumber(pageNumbers: List<Int>): Int {
        return pageNumbers[pageNumbers.size / 2]
    }

    fun part1(input: List<String>): Int {
        val middleNumbers = arrayListOf<Int>()
        val (orderingRules, pageNumbersList) = parseManual(input)
        for (pageNumbers in pageNumbersList) {
            if (conformsToRules(orderingRules, pageNumbers)) {
                middleNumbers.add(getMiddleNumber(pageNumbers))
            }
        }

        pageNumbersList

        return middleNumbers.sum()
    }

    fun fixPageNumbers(orderingRules: Map<Int,Set<Int>>, pageNumbers: List<Int>): List<Int> {
        val mutablePageNumbers = pageNumbers.toMutableList()
        val pageNumbersSet = pageNumbers.toSet()
        for (pre in orderingRules.keys) {
            for (post in orderingRules.getOrDefault(pre, setOf())) {
                if (!pageNumbersSet.contains(pre) || !pageNumbersSet.contains(post)) {
                    continue
                }
                val preIndex = mutablePageNumbers.indexOf(pre)
                val postIndex = mutablePageNumbers.indexOf(post)
                if (preIndex > postIndex) {
                    mutablePageNumbers.removeAt(preIndex)
                    mutablePageNumbers.add(postIndex, pre)
                }
            }
        }
        return mutablePageNumbers
    }

    fun part2(input: List<String>): Int {
        val middleNumbers = arrayListOf<Int>()
        val (orderingRules, pageNumbersList) = parseManual(input)
        for (pageNumbers in pageNumbersList) {
            if (!conformsToRules(orderingRules, pageNumbers)) {
                val fixedPageNumbers = fixPageNumbers(orderingRules, pageNumbers)
                middleNumbers.add(getMiddleNumber(fixedPageNumbers))
            }
        }

        return middleNumbers.sum()
    }

    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    check(part1(testInput) == 143, { "${part1(testInput)}" })

    check(part2(testInput) == 123, { "${part2(testInput)}" })

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
