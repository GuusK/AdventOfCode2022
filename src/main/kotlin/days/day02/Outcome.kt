package days.day02

enum class Outcome(val points: Int) {
    Lose(0),
    Draw(3),
    Win(6);

    companion object {
        fun fromLetter(letter: Char): Outcome {
            if (letter == 'X') {
                return Lose;
            }
            if (letter == 'Y') {
                return Draw;
            }
            return Win
        }
    }
}