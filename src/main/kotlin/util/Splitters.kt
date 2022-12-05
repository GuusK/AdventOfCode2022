package util

fun numToListOfDigits(input: Int): List<Int> {
    var res = listOf<Int>()
    var tmp = input
    while (tmp > 0) {
        res = res + tmp % 10
        tmp /= 10
    }
    return res.reversed()
}

fun stringToListOfDigits(input: String): List<Int> {
    return input.split("").filter { it != "" }.map(String::toInt)
}


val regex: Regex = Regex("\\d+")

fun allInts(input: String): List<Int> {
    return regex.findAll(input).map { it.value.toInt() }.toList()
}