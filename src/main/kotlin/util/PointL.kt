package util

import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

data class PointL(val x: Long, val y: Long) {

    constructor(x: Int, y: Int) : this(x.toLong(), y.toLong()) {
    }

    fun manhattanDistance(): Long {
        return abs(x) + abs(y)
    }

    fun manhattanDistance(other: PointL): Long {
        return abs(this.x - other.x) + abs(this.y - other.y)
    }

    fun dist(other: PointL): Double {
        val xdif = this.x - other.x
        val ydif = this.y - other.y
        return sqrt((xdif * xdif + ydif * ydif).toDouble())
    }

    fun angle(other: PointL): Double {
        return atan2((other.x - this.x).toDouble(), (other.y - this.y).toDouble())
    }

    operator fun plus(other: PointL): PointL {
        return PointL(this.x + other.x, this.y + other.y)
    }
}