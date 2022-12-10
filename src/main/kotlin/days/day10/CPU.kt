package days.day10

class CPU(val operations: List<Operation>) {

    private var register: Int = 1
    private var cycle: Int = 0

    private val signalStrengths = mutableMapOf<Int, Int>()

    private val crtWidth = 40
    private val crtHeight = 6
    private val crt: Array<Array<Char>> = Array(crtHeight) { Array(crtWidth) { '-' } }

    private fun determinceCrtWriteValue(): Char {
        return if (cycle % crtWidth in register - 1..register + 1) {
            '█'
        } else {
            ' '
        }
    }

    private fun incrementCycle() {
        crt[cycle / crtWidth][cycle % crtWidth] = determinceCrtWriteValue()
        cycle++
        if (cycle % 40 == 20) {
            signalStrengths[cycle] = register * cycle
        }

    }

    private fun execAddx(operation: Operation) {
        incrementCycle()
        incrementCycle()
        register += operation.param
    }

    private fun execNoop() {
        incrementCycle()
    }

    private fun executeOperation(operation: Operation) {
        when (operation.instruction) {
            Instruction.noop -> execNoop()
            Instruction.addx -> execAddx(operation)
        }
    }

    fun execute() {
        operations.forEach(::executeOperation)
    }

    fun getSumSignalStrengths(cycles: List<Int>): Int {
        return cycles.mapNotNull { signalStrengths[it] }
            .sum()
    }

    fun printCRT() {
        crt.forEach { line ->
            line.forEach { print(it) }
            print("\n")
        }
    }
}