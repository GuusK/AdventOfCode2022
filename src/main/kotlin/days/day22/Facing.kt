package days.day22

import java.lang.IllegalArgumentException

enum class Facing(val worth: Int) {
    Right(0),
    Down(1),
    Left(2),
    Up(3);
    
    fun rotate(char: Char) : Facing {
        val values = enumValues<Facing>()
        val index = values.indexOf(this)
        return when(char){
            'R' -> values[(index + 1) % values.size]
            'L' -> values[Math.floorMod(index - 1, values.size)]
            else -> throw IllegalArgumentException("Illegal rotation")
        }
    }
}