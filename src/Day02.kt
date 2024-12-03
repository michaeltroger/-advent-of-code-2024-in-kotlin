fun main() {

    fun isSafePart1(
        expectIncreasingLevels: Boolean,
        currentLevel: Int,
        nextLevel: Int,
    ): Boolean {
        return (
                (expectIncreasingLevels && ((nextLevel - currentLevel) in 1..3)) ||
                (!expectIncreasingLevels && ((nextLevel - currentLevel) in -3..-1))
                )
    }

    fun part1(input: List<String>): Int {
       return input.count { report ->
           val levels = report.split(" ").map { level ->
               level.toInt()
           }

           val expectIncreasingLevels = levels[1] > levels[0]
           (1..<levels.size).forEach { index ->
               if (!isSafePart1(
                       expectIncreasingLevels = expectIncreasingLevels,
                       currentLevel = levels[index - 1],
                       nextLevel = levels[index]
               )) {
                   return@count false
               }
           }
           true
       }
    }

    fun isSafePart2(input: List<Int>): Boolean {
        return input.windowed(2).all { (current, next) ->
            next - current in 1..3
        } || input.windowed(2).all { (current, next) ->
            next - current in -3..-1
        }
    }

    fun part2(input: List<String>): Int {
        return input.count { report ->
            val levels = report.split(" ").map { level ->
                level.toInt()
            }

            isSafePart2(levels) || levels.indices.any { index ->
                isSafePart2(levels.toMutableList().apply { removeAt(index) })
            }
        }
    }

    // part1
    val testInput = readInput("Day02_test")
    val input = readInput("Day02")

    check(part1(testInput) == 2) { "part1 check failed" }
    part1(input).println()

    // part2
    check(part2(testInput) == 4) { "part2 check failed" }
    part2(input).println()
}
