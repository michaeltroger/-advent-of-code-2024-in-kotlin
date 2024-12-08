fun main() {

    fun part1(input: List<String>): Int {
        val coordinatesStartLetter: List<Pair<Int, Int>> = buildList {
            input.forEachIndexed { y, row ->
                "X".toRegex().findAll(row).toList().map { match ->
                    add(match.range.first to y)
                }
            }
        }

        return buildList {
            coordinatesStartLetter.forEach { coordinates ->
                val (x, y) = coordinates
                if (
                    input.getOrNull(y-3)?.getOrNull(x-3) == 'S' &&
                    input.getOrNull(y-2)?.getOrNull(x-2) == 'A' &&
                    input.getOrNull(y-1)?.getOrNull(x-1) == 'M'
                    ) {
                    add(coordinates)
                }
                if (
                    input.getOrNull(y+3)?.getOrNull(x+3) == 'S' &&
                    input.getOrNull(y+2)?.getOrNull(x+2) == 'A' &&
                    input.getOrNull(y+1)?.getOrNull(x+1) == 'M'
                    ) {
                    add(coordinates)
                }
                if (
                    input.getOrNull(y-3)?.getOrNull(x+3) == 'S' &&
                    input.getOrNull(y-2)?.getOrNull(x+2) == 'A' &&
                    input.getOrNull(y-1)?.getOrNull(x+1) == 'M'
                ) {
                    add(coordinates)
                }
                if (
                    input.getOrNull(y+3)?.getOrNull(x-3) == 'S' &&
                    input.getOrNull(y+2)?.getOrNull(x-2) == 'A' &&
                    input.getOrNull(y+1)?.getOrNull(x-1) == 'M'
                ) {
                    add(coordinates)
                }
                if (
                    input.getOrNull(y+3)?.getOrNull(x) == 'S' &&
                    input.getOrNull(y+2)?.getOrNull(x) == 'A' &&
                    input.getOrNull(y+1)?.getOrNull(x) == 'M'
                ) {
                    add(coordinates)
                }
                if (
                    input.getOrNull(y)?.getOrNull(x-3) == 'S' &&
                    input.getOrNull(y)?.getOrNull(x-2) == 'A' &&
                    input.getOrNull(y)?.getOrNull(x-1) == 'M'
                ) {
                    add(coordinates)
                }
                if (
                    input.getOrNull(y-3)?.getOrNull(x) == 'S' &&
                    input.getOrNull(y-2)?.getOrNull(x) == 'A' &&
                    input.getOrNull(y-1)?.getOrNull(x) == 'M'
                ) {
                    add(coordinates)
                }
                if (
                    input.getOrNull(y)?.getOrNull(x+3) == 'S' &&
                    input.getOrNull(y)?.getOrNull(x+2) == 'A' &&
                    input.getOrNull(y)?.getOrNull(x+1) == 'M'
                ) {
                    add(coordinates)
                }
            }
        }.count()
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
            var bottomLeftToTopRightFound = false
            var bottomRightToTopLeftFound = false

            if (
                (
                    input.getOrNull(y + 1)?.getOrNull(x + 1) == 'M' &&
                    input.getOrNull(y - 1)?.getOrNull(x - 1) == 'S'
                ) ||
                (
                    input.getOrNull(y - 1)?.getOrNull(x - 1) == 'M' &&
                    input.getOrNull(y + 1)?.getOrNull(x + 1) == 'S'
                )
            ) {
                bottomRightToTopLeftFound = true
            }

            if (
                (
                    input.getOrNull(y - 1)?.getOrNull(x + 1) == 'M' &&
                    input.getOrNull(y + 1)?.getOrNull(x - 1) == 'S'
                ) ||
                (
                    input.getOrNull(y + 1)?.getOrNull(x - 1) == 'M' &&
                    input.getOrNull(y - 1)?.getOrNull(x + 1) == 'S'
                )
            ) {
                bottomLeftToTopRightFound = true
            }

            if (bottomLeftToTopRightFound && bottomRightToTopLeftFound) {
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