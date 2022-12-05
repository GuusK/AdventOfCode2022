package days.day05

import util.allInts

data class Op(val num: Int, val from: Int, val to: Int) {
    
    companion object {
        fun fromString(input: String): Op {
            val res = allInts(input)
            return Op(res[0], res[1] - 1, res[2] - 1)
        }
    }
}