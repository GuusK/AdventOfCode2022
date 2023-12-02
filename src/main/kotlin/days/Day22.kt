package days

import days.day22.Facing
import days.day22.Instruction
import days.day22.MonkeyMapState
import days.day22.Space
import resources.InputReader
import util.Point

object Day22 : DayInterface {
    override val dayNumber: Int
        get() = 22

    val map: Map<Point, Space>
    val instrs: List<Instruction>

    val path: MutableMap<Point, Facing> = mutableMapOf()

    init {
        val linesep = System.getProperty("line.separator")
//        val input = InputReader.getResourceString("./input/day22example.txt")
        val input = InputReader.getResourceString(22)
        val splitted = input.split(linesep + linesep)
        map = parseMap(splitted[0])
        instrs = Instruction.listFromString(splitted[1])
    }

    fun parseMap(input: String): Map<Point, Space> {
        val lines = input.split("\n")
        return lines.withIndex().map { line ->
            line.value.withIndex().map { pos ->
                when (pos.value) {
                    '.' -> Point(pos.index, line.index) to Space.Open
                    '#' -> Point(pos.index, line.index) to Space.Wall
                    else -> Point(pos.index, line.index) to Space.Empty
                }
            }.toMap()
        }
            .flatMap { it.entries }
            .associate { it.key to it.value }
            .filter { it.value != Space.Empty }
    }

    fun printMap(map: Map<Point, Space>) {
        val maxX = map.maxBy { it.key.x }.key.x
        val maxY = map.maxBy { it.key.y }.key.y

        println("=========================================================")
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                val p = Point(x, y)
                if(path.contains(p)){
                    print(path[p]!!.char)
                } else {
                    print(map.getOrDefault(p, Space.Empty).char)
                }
            }
            print('\n')
        }
        println("=========================================================")
    }

    fun nextPosition(position: Point, facing: Facing): Point {
        val posNext = when (facing) {
            Facing.Left -> position + Point(-1, 0)
            Facing.Right -> position + Point(1, 0)
            // Map works that going down has increasing y index, as top row is index 0\
            Facing.Down -> position + Point(0, 1)
            Facing.Up -> position + Point(0, -1)
        }
        return if (map.containsKey(posNext)) {
            posNext
        } else {
            // we need to wrap around
            when (facing) {
                Facing.Up -> Point(position.x, map.filter { it.key.x == position.x }.maxBy { it.key.y }.key.y)
                Facing.Down -> Point(position.x, map.filter { it.key.x == position.x }.minBy { it.key.y }.key.y)

                Facing.Right -> Point(map.filter { it.key.y == position.y }.minBy { it.key.x }.key.x, position.y)
                Facing.Left -> Point(map.filter { it.key.y == position.y }.maxBy { it.key.x }.key.x, position.y)
            }
        }
    }

    fun walkMap(begin: Point): Pair<Point, Facing> {
        println("Start walking at $begin")
        var facing: Facing = Facing.Right
        var position: Point = begin
        path.put(position, facing)
        for (instr in instrs) {
            for (step in 0 until instr.distance) {
                val nextPos = nextPosition(position, facing)
                if (map[nextPos] == Space.Wall) {
                    // position not possible, so we abort stepping further
                    break
                }
                position = nextPos
                path.put(position, facing)
//                printMap(map)
            }
            facing = facing.rotate(instr.rotation)
        }
        return Pair(position, facing)
    }


    override fun part1(): Any {
        val beginX = map
            .filter { it.key.y == 0 && it.value.char == '.'}
            .minBy { it.key.x }
            .key.x
        val begin = Point(beginX, 0)
//        printMap(map)
        val res = walkMap(begin)
        print("Finished walking at $res")

        return (res.first.y + 1) * 1000 + (res.first.x + 1) * 4 + res.second.worth
    }

    override fun part2(): Any {
        TODO("Not yet implemented")
    }
}