package resources

import java.io.File

object InputReader {

    fun stringifyDate(day: Int): String {
        return if (day < 10) "0$day" else day.toString()
    }

    private fun getDayLoc(day: Int): String {
        return "./input/day${stringifyDate(day)}.txt"
    }

    private fun getDayInput(day: Int): File {
        return File(getDayLoc(day))
    }

    private fun getInput(filename: String): File {
        return File(filename)
    }

    fun getResourceString(day: Int): String {
        return getDayInput(day).readText()
    }

    fun getResourceLines(day: Int): List<String> {
        return getDayInput(day).readLines()
    }

    fun getResourceString(filename: String): String {
        return getInput(filename).readText()
    }

    fun getResourceLines(filename: String): List<String> {
        return getInput(filename).readLines()
    }

    fun get2DIntLists(day: Int): List<List<Int>> {
        return get2DIntLists(getDayLoc(day))
    }

    fun get2DIntLists(loc: String): List<List<Int>> {
        return getResourceLines(loc).map {
            it.toCharArray().map { char ->
                char.toString().toInt()
            }
        }
    }
}