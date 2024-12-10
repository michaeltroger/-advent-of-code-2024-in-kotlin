fun main() {

    fun List<String>.getChar(x: Int, y: Int): Char? {
        return getOrNull(y)?.getOrNull(x)
    }

    fun part1(input: List<String>): Int {
        val coordinatesStartLetter: List<Pair<Int, Int>> = buildList {
            input.forEachIndexed { y, row ->
                "X".toRegex().findAll(row).toList().map { match ->
                    add(match.range.first to y)
                }
            }
        }

        return coordinatesStartLetter.map { coordinates ->
            val (x, y) = coordinates
            listOf(
                "${input.getChar(x-1, y-1)}${input.getChar(x-2, y-2)}${input.getChar(x-3, y-3)}",
                "${input.getChar(x+1, y+1)}${input.getChar(x+2, y+2)}${input.getChar(x+3, y+3)}",
                "${input.getChar(x+1, y-1)}${input.getChar(x+2, y-2)}${input.getChar(x+3, y-3)}",
                "${input.getChar(x-1, y+1)}${input.getChar(x-2, y+2)}${input.getChar(x-3, y+3)}",
                "${input.getChar(x, y+1)}${input.getChar(x, y+2)}${input.getChar(x, y+3)}",
                "${input.getChar(x-1, y)}${input.getChar(x-2, y)}${input.getChar(x-3, y)}",
                "${input.getChar(x, y-1)}${input.getChar(x, y-2)}${input.getChar(x, y-3)}",
                "${input.getChar(x+1, y)}${input.getChar(x+2, y)}${input.getChar(x+3, y)}"
            ).filter {
                it == "MAS"
            }
        }.flatten().count()
    }

    fun part2(input: List<String>): Int {
        val coordinatesStartLetter: List<Pair<Int, Int>> = buildList {
            input.forEachIndexed { y, row ->
                "A".toRegex().findAll(row).toList().map { match ->
                    add(match.range.first to y)
                }
            }
        }

        return coordinatesStartLetter.mapNotNull {  coordinates ->
            val (x, y) = coordinates

            val xText = "${input.getChar(x+1, y+1)}${input.getChar(x-1, y-1)}${input.getChar(x-1, y+1)}${input.getChar(x+1, y-1)}"

            if (xText in listOf("MSSM", "MSMS", "SMSM", "SMMS")) {
                coordinates
            } else {
                null
            }
        }.count()
    }

    // part1
    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    check(part1(testInput) == 18) { "part1 check failed" }
    part1(input).println()

    // part2
    check(part2(testInput) == 9) { "part2 check failed" }
    part2(input).println()
}
