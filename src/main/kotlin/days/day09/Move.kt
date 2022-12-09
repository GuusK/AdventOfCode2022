package days.day09

data class Move(val direction: Direction, val steps: Int){
    
    companion object {
        fun fromString(input: String): Move {
            val splitted = input.split(" ")
            return Move(Direction.fromLetter(splitted[0][0]), splitted[1].toInt())
        }
    }
}
