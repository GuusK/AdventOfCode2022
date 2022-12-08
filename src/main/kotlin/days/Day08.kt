package days

import resources.InputReader

object Day08 : DayInterface {
    override val dayNumber: Int
        get() = 8

    private lateinit var processed: List<List<Int>>
    private var width: Int = -1
    private var height: Int = -1

    init {
        readInput(InputReader.get2DIntLists(8))
    }

    fun readInput(input: List<List<Int>>) {
        processed = input
        width = processed[0].size
        height = processed.size
    }

    private fun determineVisible(x: Int, y: Int): Boolean {
        val value = processed[y][x]
        return !listOf(
            (0 until y).map { processed[it][x] }.any { it >= value },
            (y + 1 until processed.size).map { processed[it][x] }.any { it >= value },

            (0 until x).map { processed[y][it] }.any { it >= value },
            (x + 1 until processed.size).map { processed[y][it] }.any { it >= value },
        ).all { it }
    }

    override fun part1(): Any {
        val outside = width * 2 + height * 2 - 4
        val inside = (1 until height - 1).map { y ->
            (1 until width - 1).map { x ->
                determineVisible(x, y)
            }.count{it}
        }.sum()
        return outside + inside
    }

    private fun determineScenicScore1D(range: List<Pair<Int, Int>>, currHeight: Int): Int {
        var numLower = 0
        for (idx in range) {
            numLower++
            if (processed[idx.second][idx.first] >= currHeight) {
                break
            }
        }
        return numLower
    }

    private fun determineScenicScore(x: Int, y: Int): Int {
        val currHeight = processed[y][x]
        val visibleTrees = listOf(
            determineScenicScore1D((x - 1 downTo 0).toList().map { Pair(it, y) }, currHeight),
            determineScenicScore1D((x + 1 until processed[y].size).toList().map { Pair(it, y) }, currHeight),
            determineScenicScore1D((y - 1 downTo 0).toList().map { Pair(x, it) }, currHeight),
            determineScenicScore1D((y + 1 until processed[x].size).toList().map { Pair(x, it) }, currHeight)
        )

        return visibleTrees.reduce { acc, i -> acc * i }
    }

    override fun part2(): Any {
        var bestScenic = -1
        for (y in 1 until height - 1) {
            for (x in 1 until width - 1) {
                val score = determineScenicScore(x, y)
                if (score > bestScenic) {
                    bestScenic = score
                }
            }
        }
        return bestScenic
    }

}