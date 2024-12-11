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
            visitedMap[guard.position.x][guard.position.y] = 'X'

            val tempGuard = guard.copy().apply {
                position = getNextPosition(direction, position)
            }
            var potentialNextChar: Char? = getCharForPosition(map, tempGuard.position) ?: break
            while (potentialNextChar == '#') {
                tempGuard.direction = getNextDirectionAfterCollision(tempGuard.direction)
                tempGuard.position = getNextPosition(tempGuard.direction, guard.position)
                potentialNextChar = getCharForPosition(map, tempGuard.position)
            }
            guard.direction = tempGuard.direction
            guard.position = tempGuard.position
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
        val guard = Guard(position = originalGuardPosition, direction = Direction.UP)

        var validObstructions = 0

        map.forEachIndexed { y, row ->
            row.forEachIndexed { x, _ ->
                if (map[y][x] == '.') {
                    val tempGuard = guard.copy()
                    val obstructionTestMap = map.map {
                        it.clone()
                    }.toTypedArray()
                    obstructionTestMap[y][x] = '#'

                    while (true) {
                        var nextPosition = getNextPosition(tempGuard.direction, tempGuard.position)
                        var nextChar: Char? = getCharForPosition(obstructionTestMap, nextPosition) ?: break
                        while (nextChar == '#') {
                            tempGuard.direction = getNextDirectionAfterCollision(tempGuard.direction)
                            nextPosition = getNextPosition(tempGuard.direction, tempGuard.position)
                            nextChar = getCharForPosition(obstructionTestMap, nextPosition)
                        }
                        tempGuard.position = nextPosition

                        if (tempGuard.direction.char == obstructionTestMap[tempGuard.position.y][tempGuard.position.x]) {
                            validObstructions++
                            break
                        }
                        if (obstructionTestMap[tempGuard.position.y][tempGuard.position.x] == '.') {
                            obstructionTestMap[tempGuard.position.y][tempGuard.position.x] = tempGuard.direction.char
                        }
                    }
                }
            }
        }

        return validObstructions
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
