package days.day07

data class Dir(
    val name: String,
    val parent: Dir? = null,
    var files: List<File> = listOf(),
    var dirs: List<Dir> = listOf(),
    var size: Int = 0
    
) {
    override fun toString(): String {
        return "Dir(name='$name', files=$files, dirs=${dirs.size})"
    }
}