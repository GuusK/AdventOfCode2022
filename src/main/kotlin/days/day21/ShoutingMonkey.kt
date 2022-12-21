package days.day21

import util.allInts

data class ShoutingMonkey(
    val type: MonkeyType,
    val id: String,
    val num: Long,
    val left: String?,
    val right: String?,
    val operation: Char
) {
    companion object {
        fun fromString(line: String): ShoutingMonkey {
            val splitted = line.split(":")
            val id = splitted[0]
            val ints = allInts(splitted[1])
            
            if(ints.isNotEmpty()){
                return ShoutingMonkey(MonkeyType.Number, id, ints[0].toLong(), null, null, 'n')
            }
            val rightPart = splitted[1].trim()
            
            val left = rightPart.substring(0, 4)
            val op = rightPart.substring(5,6).toCharArray()[0]
            val right = rightPart.substring(7, rightPart.length)
            return ShoutingMonkey(MonkeyType.Operation, id, -1, left, right, op)
        }
    }
}
