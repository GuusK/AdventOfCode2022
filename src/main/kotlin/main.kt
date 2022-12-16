import days.*

val days = listOf(
    Day01,
    Day02,
    Day03,
    Day04,
    Day05,
    Day06,
    Day07,
    Day08,
    Day09,
    Day10,
    Day11,
    Day12,
    Day13,
    Day14,
    Day15,
    Day16
)

fun main(args: Array<String>) {
    val day = if (args.isEmpty()) {
        -1
    } else {
        args[0].toInt() - 1
    }

    if (day in days.indices) {
        run(days[day])
    } else {
        days.indices
            .forEach { x -> run(days[x]) }
    }
}

fun run(day: DayInterface) {
    val start1 = System.currentTimeMillis()
    val p1 = day.part1()
    val dur1 = System.currentTimeMillis() - start1
    println("Ran day ${day.dayNumber}")
    println("Part 1: $p1")
    println("Part 1 took $dur1 ms")

    val start2 = System.currentTimeMillis()
    val p2 = day.part2()
    val dur2 = System.currentTimeMillis() - start2
    println("Part 2: $p2")
    println("Part 2 took $dur2 ms")
    println()
}