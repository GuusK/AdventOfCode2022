package days

import days.day07.Dir
import days.day07.File
import resources.InputReader

object Day07 : DayInterface {
    override val dayNumber: Int
        get() = 7

    private val root: Dir = Dir("/")
    init {
        readInput(InputReader.getResourceLines("./input/day07.txt"))
    }

    fun readInput(input: List<String>){
        var currIdx = 1
        var curDir: Dir = root
        root.dirs = listOf()
        root.files = listOf()
        while (currIdx < input.size) {
            val line = input[currIdx]
            if (line.equals("$ ls")) {
                var resLine = input[currIdx + 1]
                while (!resLine.startsWith("$ ")) {
                    if (!resLine.startsWith("dir ")) {
                        val splitted = resLine.split(" ")
                        curDir.files += File(splitted[1], splitted[0].toInt())
                    }

                    currIdx++
                    if (currIdx >= input.size - 1) {
                        break
                    }
                    resLine = input[currIdx + 1]
                }
            } else if (line.startsWith("$ cd ..")) {
                curDir = curDir.parent!!
            } else if (line.startsWith("$ cd ")) {
                val newDir = Dir(name = line.split(" ").last(), parent = curDir)
                curDir.dirs += newDir
                curDir = newDir
            }
            currIdx++
        }
        calcSizes(root)
    }

    private fun calcSizes(dir: Dir) {
        dir.dirs.forEach { calcSizes(it) }
        dir.size = dir.files.sumOf { it.size } + dir.dirs.sumOf { it.size }
    }

    private fun sumUpto(dir: Dir, limit: Int): Int{
        val childSum = dir.dirs.sumOf { sumUpto(it, limit) }
        if(dir.size <= limit){
            return dir.size + childSum 
        } else {
            return childSum
        }
    }
    
    private fun findDeleteSize(dir: Dir, needed: Int, currSmall: Int): Int{
        val childRes = dir.dirs.minOfOrNull { findDeleteSize(it, needed, currSmall) }
        return if(childRes != null && (childRes in needed  until currSmall)){
            childRes
        } else if (dir.size in needed until currSmall){
            dir.size
        } else {
            currSmall
        }
    }
    
    override fun part1(): Any {
        return sumUpto(root, 100000)
    }

    override fun part2(): Any {
        val totalSize = 70000000
        val needed = 30000000
        val unused = totalSize - root.size
        val toDelete = needed - unused 
        return findDeleteSize(root, toDelete, Int.MAX_VALUE)
    }
}