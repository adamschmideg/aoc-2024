
sealed class Item {
    data object Obstacle : Item()
    data object Route: Item()
    data object Empty : Item()
    data class Guard(var dx: Int, var dy: Int) : Item() {
        // (0, 1) -> (1, 0) -> (0, -1) -> (-1, 0) -> (0, 1)
        // (0, -1) -> (1, 0) -> (0, 1) -> (-1, 0)
        fun turnRight() {
            val tmp = dy
            dy = dx
            dx = -tmp
        }

        override fun toString(): String {
            if (dx == 0 && dy == -1) return "^"
            if (dx == 1 && dy == 0) return ">"
            if (dx == 0 && dy == 1) return "v"
            if (dx == -1 && dy == 0) return "<"
            return "?"
        }
    }

    companion object {
        fun parseItem(char: Char): Item = when (char) {
            '#' -> Obstacle
            'X' -> Route
            '^' -> Guard( 0, -1) // top left is zero
            else -> Empty
        }
    }
}

data class Location(val x: Int, val y: Int) {}

data class Lab(val map: Array<Array<Item>>, var guardLocation: Location?) {
    companion object {
        fun build(rawInput: List<String>): Lab {
            var map = arrayOf<Array<Item>>()
            var loc: Location? = null
            for (i in rawInput.indices) {
                var row = arrayOf<Item>()
                for (c in rawInput[i]) {
                    val item = Item.parseItem(c)
                    row += item
                }
                map += row
                for (j in row.indices) {
                    if (row[j] is Item.Guard) {
                        loc = Location(j, i)
                    }
                }
            }
            return Lab(map, loc)
        }
    }

    fun isOutside(loc: Location): Boolean = loc.y < 0 || loc.y >= map.size || loc.x < 0 || loc.x >= map[0].size

    fun debugString(): String {
        var s = ""
        for (row in map) {
            for (item in row) {
                s += when (item) {
                    is Item.Obstacle -> '#'
                    is Item.Route -> 'X'
                    is Item.Empty -> '.'
                    is Item.Guard -> item.toString()
                }
            }
            s += "\n"
        }
        s += "------------\n"
        return s
    }

    fun walkUntilDone() {
        while (true) {
            // println(debugString())

            val loc = guardLocation
            if (loc == null) break
            val guard = map[loc.y][loc.x] as Item.Guard
            val next = Location(loc.x + guard.dx, loc.y + guard.dy)
            if (isOutside(next)) {
                map[loc.y][loc.x] = Item.Route
                guardLocation = null
                break
            }
            val item = map[next.y][next.x]
            if (item is Item.Obstacle) {
                guard.turnRight()
                continue
            }
            if (item is Item.Route || item is Item.Empty) {
                map[loc.y][loc.x] = Item.Route
                map[next.y][next.x] = guard
                guardLocation = next
                continue
            }
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val lab = Lab.build(input)
        lab.walkUntilDone()
        var countRoute = 0
        for (row in lab.map) {
            for (item in row) {
                if (item is Item.Route) countRoute++
            }
        }
        return countRoute
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

    check(part1(testInput) == 41, { "${part1(testInput)}" })

    // check(part2(testInput) == 123, { "${part2(testInput)}" })

    println("Part 1: ${part1(input)}")
    // println("Part 2: ${part2(input)}")
}
