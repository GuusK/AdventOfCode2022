package days.day16

data class State(
    val curValve: String,
    val timeleft: Int,
    val sumFlow: Int,
    val opened: Set<String>,
)