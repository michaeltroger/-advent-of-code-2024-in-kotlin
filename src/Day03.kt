fun main() {

    fun part1(input: List<String>): Int {
        val parsedInput = input.joinToString(" ")

        return """mul\((\d{1,3}),(\d{1,3})\)""".toRegex().findAll(parsedInput)
            .sumOf {
                val (_, first, second) = it.groupValues
                first.toInt() * second.toInt()
            }
    }

    fun part2(input: List<String>): Int {
        val parsedInput = input.joinToString(" ")

        var isEnabled = true
        return """mul\((\d{1,3}),(\d{1,3})\)|don't\(\)|do\(\)""".toRegex().findAll(parsedInput).map {
            it.groupValues
        }.sumOf {
            if (it[0].startsWith("do()")) {
                isEnabled = true
            } else if (it[0].startsWith("don't")) {
                isEnabled = false
            }
            if (isEnabled && it[0].startsWith("mul")) {
                it[1].toInt() * it[2].toInt()
            } else {
                0
            }
        }
    }

    // part1
    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    check(part1(testInput) == 161) { "part1 check failed" }
    part1(input).println()

    // part2
    val testInput2 = readInput("Day03_test2")
    check(part2(testInput2) == 48) { "part2 check failed" }
    part2(input).println()
}
