package util

import kotlin.math.min

data class Vec3(var x: Int, var y: Int, var z: Int) {

    constructor() : this(0, 0, 0)

    constructor(x: Int, y: Int) : this(x, y, 0)

    operator fun plus(other: Vec3): Vec3 {
        return Vec3(this.x + other.x, this.y + other.y, this.z + other.z)
    }

    fun touching(that: Vec3): Boolean {
        return kotlin.math.abs(this.x - that.x) <= 1
                && kotlin.math.abs(this.y - that.y) <= 1
                && kotlin.math.abs(this.z - that.z) <= 1
    }
    
    fun inverse(): Vec3 {
        return Vec3(-x, -y, -z)
    }

    fun product(): Int {
        return x * y * z
    }

    fun abs(): Vec3 {
        return Vec3(kotlin.math.abs(x), kotlin.math.abs(y), kotlin.math.abs(z))
    }

    fun sum(): Int {
        return x + y + z
    }

    fun hasNegative(): Boolean {
        return x < 0 || y < 0 || z < 0
    }

    operator fun get(idx: Int): Int {
        return when (idx) {
            0 -> x
            1 -> y
            2 -> z
            else -> error("Vec3.get out of range with position $idx")
        }
    }

    operator fun set(idx: Int, value: Int) {
        when (idx) {
            0 -> x = value
            1 -> y = value
            2 -> z = value
            else -> error("Vec3.get out of range with position $idx")
        }
    }

    fun containsZero(): Boolean {
        return x == 0 || y == 0 || z == 0
    }

    fun isZero(): Boolean {
        return x == 0 && y == 0 && z == 0
    }

    private fun gcd(first: Long, second: Long): Long {
        var a = first
        var b = second
        while (b > 0) {
            val temp = b
            b = a % b // % is remainder
            a = temp
        }
        return a
    }

    fun gcd(): Long {
        return gcd(gcd(x.toLong(), y.toLong()), z.toLong())
    }

    private fun lcm(first: Long, second: Long): Long {
        return first * (second / gcd(first, second))
    }

    fun lcm(): Long {
        return lcm(lcm(x.toLong(), y.toLong()), z.toLong())
    }

    companion object {
        fun fromList(nums: List<Int>, until: Int): Vec3 {
            val res = Vec3()
            for (idx in 0..min(min(until, nums.size), 3)) {
                res[idx] = nums[idx]
            }
            return res
        }
    }
}