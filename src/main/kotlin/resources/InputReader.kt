package resources

import java.io.File

object InputReader {

    fun stringifyDate(day: Int) : String {
        return if (day < 10) "0$day" else day.toString()
    }

    private fun getDayInput(dayNum: String): File{
        return File("./input/day$dayNum.txt")
    }

    private fun getDayInput(day: Int): File {
        return getDayInput(stringifyDate(day))
    }

    private fun getInput(filename: String): File {
        return File(filename)
    }

    fun getResourceString(day: Int): String {
        return getDayInput(day).readText()
    }

    fun getResourceLines(day: Int): List<String>{
        return getDayInput(day).readLines()
    }

    fun getResourceString(filename: String): String {
        return getInput(filename).readText()
    }

    fun getResourceLines(filename: String): List<String>{
        return getInput(filename).readLines()
    }
}