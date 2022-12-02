import days.*

val days = listOf(
    Day01,
    Day02
)

fun main(args: Array<String>) {
    val day = if (args.isEmpty()) {
        -1
    } else {
        args[0].toInt() - 1
    }

    if (day in days.indices) {
        run(days[day])
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