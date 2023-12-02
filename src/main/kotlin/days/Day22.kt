package days

import days.day22.Facing
import days.day22.Instruction
import days.day22.Space
import resources.InputReader
import util.Point

object Day22 : DayInterface {
    override val dayNumber: Int
        get() = 22

    val map: Map<Point, Space>
    val instrs: List<Instruction>

    init {
        val input = InputReader.getResourceString("./input/day22example.txt")
        val splitted = input.split("\n\n")
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

        for (y in 0..maxY) {
            for (x in 0..maxX) {
                val p = Point(x, y)
                print(map.getOrDefault(p, Space.Empty).char)
            }
            print('\n')
        }
    }

    fun nextPosition(position: Point, facing: Facing): Point {
        val posNext = when (facing) {
            Facing.Right -> position + Point(1, 0)
            Facing.Down -> position + Point(-1, 0)
            Facing.Left -> position + Point(-1, 0)
            Facing.Up -> position + Point(1, 0)
        }
        return if (map.containsKey(posNext)) {
            posNext
        } else {
            // we need to wrap around
            when (facing) {
                Facing.Up -> Point(map.filter { it.key.x == position.x }.maxBy { it.key.x }.key.x, position.y)
                Facing.Right -> Point(map.filter { it.key.y == position.y }.minBy { it.key.y }.key.y, position.x)
                Facing.Down -> Point(map.filter { it.key.x == position.x }.minBy { it.key.x }.key.x, position.y)
                Facing.Left -> Point(map.filter { it.key.y == position.y }.maxBy { it.key.y }.key.y, position.x)
            }
        }
    }

    fun walkMap(begin: Point): Pair<Point, Facing> {
        var facing: Facing = Facing.Right
        var position: Point = begin
        for (instr in instrs) {
            for (step in 0 until instr.distance) {
                val nextPos = nextPosition(position, facing)
                if (map[nextPos] == Space.Wall) {
                    // position not possible, so we abort stepping further
                    continue
                }
                position = nextPos
                facing = facing.rotate(instr.rotation)
            }
        }
        return Pair(position, facing)
    }


    override fun part1(): Any {
        val beginX = map
            .filter { it.key.y == 0 }
            .maxBy { it.key.x }
            .key.x
        val begin = Point(beginX, 0)
        val res = walkMap(begin)

        return (res.first.x + 1) * 1000 + (res.first.y + 1) * 4 + res.second.worth
    }

    override fun part2(): Any {
        TODO("Not yet implemented")
    }
}