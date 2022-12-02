package days.day02

enum class Shape(val points: Int) {
    Rock(1),
    Paper(2),
    Scissor(3);

    companion object {
        fun fromLetter(letter: Char): Shape {
            if (letter == 'A' || letter == 'X') {
                return Rock
            }
            if (letter == 'B' || letter == 'Y') {
                return Paper
            }
            return Scissor
        }

        fun winnerOf(shape: Shape): Shape {
            if(shape == Rock){
                return Paper
            }
            if(shape == Paper){
                return Scissor
            }
            return Rock
        }

        fun loserOf(shape: Shape): Shape {
            if(shape == Rock){
                return Scissor
            }
            if(shape == Paper){
                return Rock
            }
            return Paper
        }
    }
}