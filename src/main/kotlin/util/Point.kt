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
    
    fun minVals(that: Point): Point {
        return Point(Math.min(this.x, that.x), Math.min(this.y, that.y))
    }

    fun maxVals(that: Point): Point {
        return Point(Math.max(this.x, that.x), Math.max(this.y, that.y))
    }
    
    fun fourNeighbors(): List<Point> {
        return listOf(
            this + Point(1, 0),
            this + Point(0, -1),
            this + Point(-1, 0),
            this + Point(0, 1),
        )
    }
    
    fun eightNeighbors(): List<Point>{
        return fourNeighbors() + listOf(
            this + Point(-1, -1),
            this + Point(1, -1),
            this + Point(-1, 1),
            this + Point(1, 1),
        )
    }
}