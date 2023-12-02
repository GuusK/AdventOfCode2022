package days.day22

import util.allInts
import java.lang.IllegalArgumentException
import java.util.regex.Pattern

data class Instruction(val distance: Int, val rotation: Char) {
    companion object {

        fun fromString(line: String): Instruction {
            val distance = line.substring(0, line.length - 1).toInt()
            val rotation = line[line.length - 1]
            if(rotation != 'L' && rotation != 'R'){
                throw IllegalArgumentException("Illegal rotation found: $rotation")
            }
            return Instruction(distance, rotation)
        }

        val p = Pattern.compile("\\d+[LR]");
        fun listFromString(line: String): List<Instruction> {
            val m = p.matcher(line)
            val res = mutableListOf<Instruction>()
            while (m.find()) {
                val value = m.group()
                res.add(fromString(value))
            }
            return res
        }
    }
}
