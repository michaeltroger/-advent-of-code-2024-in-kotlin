fun main() {

    fun part1(input: List<String>): Int {
        val indexLineBreak = input.indexOfFirst { it.isBlank() }
        val pageOrderingRulesMap: Map<String, List<String>> = buildMap {
            input.take(indexLineBreak).forEach { rule ->
                val ruleAsList = rule.split("|")
                val list = getOrElse(ruleAsList[0]) {
                    emptyList()
                }
                set(ruleAsList[0], list + ruleAsList[1])
            }
        }

        val updates = input.drop(indexLineBreak+1).map { update->
            update.split(",")
        }

        val comparator = Comparator<String> { valueOne, valueTwo ->
           if (pageOrderingRulesMap.contains(valueOne) && pageOrderingRulesMap[valueOne]!!.contains(valueTwo)){
               -1
           } else {
               0
           }
        }

        return updates.sumOf { update->
            val sorted = update.sortedWith(comparator)
            if (sorted == update) {
                sorted[sorted.size/2].toInt()
            } else {
                0
            }
        }
    }

    fun part2(input: List<String>): Int {
        val indexLineBreak = input.indexOfFirst { it.isBlank() }
        val pageOrderingRulesMap: Map<String, List<String>> = buildMap {
            input.take(indexLineBreak).forEach { rule ->
                val ruleAsList = rule.split("|")
                val list = getOrElse(ruleAsList[0]) {
                    emptyList()
                }
                set(ruleAsList[0], list + ruleAsList[1])
            }
        }

        val updates = input.drop(indexLineBreak+1).map { update->
            update.split(",")
        }

        val comparator = Comparator<String> { valueOne, valueTwo ->
            if (pageOrderingRulesMap.contains(valueOne) && pageOrderingRulesMap[valueOne]!!.contains(valueTwo)){
                -1
            } else {
                0
            }
        }

        return updates.sumOf { update->
            val sorted = update.sortedWith(comparator)
            if (sorted != update) {
                sorted[sorted.size/2].toInt()
            } else {
                0
            }
        }
    }

    // part1
    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    check(part1(testInput) == 143) { "part1 check failed" }
    part1(input).println()

    // part2
    check(part2(testInput) == 123) { "part2 check failed" }
    part2(input).println()
}
