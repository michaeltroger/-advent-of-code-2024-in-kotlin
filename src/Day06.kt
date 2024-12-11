enum class Direction(val char: Char) {
    UP('U'),
    DOWN('D'),
    LEFT('L'),
    RIGHT('R');
}

data class Vector(val x: Int, val y: Int)

data class Guard(var position: Vector, var direction: Direction)

fun main() {
    fun getNextPosition(guardDirection: Direction, guardPosition: Vector) = when (guardDirection) {
        Direction.UP -> Vector(guardPosition.x, guardPosition.y - 1)
        Direction.DOWN -> Vector(guardPosition.x, guardPosition.y + 1)
        Direction.LEFT -> Vector(guardPosition.x - 1, guardPosition.y)
        Direction.RIGHT -> Vector(guardPosition.x + 1, guardPosition.y)
    }

    fun getNextDirectionAfterCollision(tempGuardDirection: Direction) = when (tempGuardDirection) {
        Direction.UP -> Direction.RIGHT
        Direction.DOWN -> Direction.LEFT
        Direction.LEFT -> Direction.UP
        Direction.RIGHT -> Direction.DOWN
    }

    fun getCharForPosition(map: Array<Array<Char>>, nextPosition: Vector) =
        map.getOrNull(nextPosition.y)?.getOrNull(nextPosition.x)

    fun part1(input: List<String>): Int {
        lateinit var originalGuardPosition: Vector

        val map = input.mapIndexed { index, line ->
            if (line.contains('^')) {
                originalGuardPosition = Vector(x = line.indexOf('^'), y = index)
            }
            line.toList().toTypedArray()
        }.toTypedArray()
        val guard = Guard(position = originalGuardPosition, direction = Direction.UP)

        val visitedMap = map.map {
            it.clone()
        }.toTypedArray()

        while (true) {
            visitedMap[guard.position.y][guard.position.x] = 'X'

            var potentialNextPosition = getNextPosition(guard.direction, guard.position)
            while (getCharForPosition(map, potentialNextPosition) == '#') {
                guard.direction = getNextDirectionAfterCollision(guard.direction)
                potentialNextPosition = getNextPosition(guard.direction, guard.position)
            }
            if (getCharForPosition(map, potentialNextPosition) == null) {
                break
            }
            guard.position = potentialNextPosition
        }
        return visitedMap.flatten().count { it == 'X' }
    }

    fun part2(input: List<String>): Int {
        lateinit var originalGuardPosition: Vector

        val map = input.mapIndexed { index, line ->
            if (line.contains('^')) {
                originalGuardPosition = Vector(x = line.indexOf('^'), y = index)
            }
            line.toList().toTypedArray()
        }.toTypedArray()
        val originalGuard = Guard(position = originalGuardPosition, direction = Direction.UP)

        var detectedLoop = 0

        map.forEachIndexed { y, row ->
            row.forEachIndexed { x, _ ->
                if (map[y][x] == '.') {
                    val tempGuard = originalGuard.copy()
                    val obstructionTestMap = map.map {
                        it.clone()
                    }.toTypedArray().apply {
                        this[y][x] = '#'
                    }

                    while (true) {
                        var potentialNextPosition = getNextPosition(tempGuard.direction, tempGuard.position)
                        while (getCharForPosition(obstructionTestMap, potentialNextPosition) == '#') {
                            tempGuard.direction = getNextDirectionAfterCollision(tempGuard.direction)
                            potentialNextPosition = getNextPosition(tempGuard.direction, tempGuard.position)
                        }
                        if (getCharForPosition(obstructionTestMap, potentialNextPosition) == null) {
                            break
                        }
                        tempGuard.position = potentialNextPosition

                        val currentChar = obstructionTestMap[tempGuard.position.y][tempGuard.position.x]
                        if (currentChar == tempGuard.direction.char) {
                            detectedLoop++
                            break
                        } else if (currentChar == '.') {
                            obstructionTestMap[tempGuard.position.y][tempGuard.position.x] = tempGuard.direction.char
                        }
                    }
                }
            }
        }

        return detectedLoop
    }

    // part1
    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

    check(part1(testInput) == 41) { "part1 check failed" }
    part1(input).println()

    // part2
    check(part2(testInput) == 6) { "part2 check failed" }
    part2(input).println()
}
