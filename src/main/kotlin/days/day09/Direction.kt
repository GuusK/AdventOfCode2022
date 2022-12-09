package days.day09

import util.Vec3

enum class Direction(val vector: Vec3) {
    UP(Vec3(0,1)),
    RIGHT(Vec3(1,0)),
    DOWN(Vec3(0,-1)),
    LEFT(Vec3(-1,0));
    
    companion object {
        fun fromLetter(char: Char): Direction{
            return when(char){
                'U' -> UP
                'R' -> RIGHT
                'D' -> DOWN
                'L' -> LEFT
                else -> {throw IllegalArgumentException("Unknown direction letter")}
            }
        }
    }
}