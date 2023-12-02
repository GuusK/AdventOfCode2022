package days.day23

import util.Point

enum class Move (val movement: Point){
    North(Point(0, 1)),
    South(Point(0, -1)),
    West(Point(-1, 0)),
    East(Point(1,0));
    
    fun next(): Move {
        val values = enumValues<Move>()
        val index = values.indexOf(this)
        return values[Math.floorMod((index) + 1, values.size)]
    }
    
    companion object {
        fun byIndex(idx: Int): Move{
            return Move.values()[idx]
        }
    }
}