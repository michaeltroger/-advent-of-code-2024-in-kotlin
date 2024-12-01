import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val inputAsNumbers = input.map {
            it.split("   ")
                .map { it.toInt() }
        }
        val firstList = inputAsNumbers.map {
            it[0]
        }.sorted()
        val secondList = inputAsNumbers.map {
            it[1]
        }.sorted()

        return (inputAsNumbers.indices).map {
            firstList[it] to secondList[it]
        }.sumOf {
            abs(it.first - it.second)
        }
    }

    fun part2(input: List<String>): Int {
        val inputAsNumbers = input.map {
            it.split("   ")
                .map { it.toInt() }
        }
        val firstList = inputAsNumbers.map {
            it[0]
        }
        val secondList = inputAsNumbers.map {
            it[1]
        }.groupingBy { it }.eachCount()

        return (inputAsNumbers.indices).sumOf {
            val timesInSecondList = secondList[firstList[it]] ?: 0
            firstList[it] * timesInSecondList
        }
    }

    // day1
    val testInput = readInput("Day01_test")
    val input = readInput("Day01")

    check(part1(testInput) == 11) { "part1 check failed" }
    part1(input).println()

    // day2
    check(part2(testInput) == 31) { "part2 check failed" }
    part2(input).println()
}
