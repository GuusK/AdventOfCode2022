package days.day10

import java.lang.IllegalArgumentException

data class Operation(val instruction: Instruction, val param: Int){
    
    constructor(instruction: Instruction) : this(instruction, 0)
    
    companion object{
        fun fromString(input: String): Operation{
            val splitted = input.split(" ")
            return when(splitted[0]){
                "noop" -> Operation(Instruction.noop)
                "addx" -> Operation(Instruction.addx, splitted[1].toInt())
                else -> throw IllegalArgumentException("Unknown instruction")
            }
        }
    }
}
