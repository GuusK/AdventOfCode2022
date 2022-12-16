package util

import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

data class Point(val x: Int, val y: Int) {

    fun manhattanDistance(): Int {
        return abs(x) + abs(y)
    }
    
    fun manhattanDistance(other: Point): Int {
        return abs(this.x - other.x) + abs(this.y - other.y) 
    }

    fun dist(other: Point): Double{
        val xdif = this.x - other.x
        val ydif = this.y - other.y
        return sqrt((xdif * xdif + ydif * ydif).toDouble())
    }

    fun angle(other: Point): Double{
        return atan2((other.x - this.x).toDouble(), (other.y - this.y).toDouble())
    }

    operator fun plus(other: Point): Point {
        return Point(this.x + other.x, this.y + other.y)
    }
}